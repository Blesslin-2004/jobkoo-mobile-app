const functions = require("firebase-functions");
const axios = require("axios");
require("dotenv").config();

exports.searchJobs = functions.https.onCall(async (data, context) => {
  try {
    const { role, skills, locations, jobType } = data;

    if (!role || !locations?.length) {
      throw new functions.https.HttpsError(
        "invalid-argument",
        "Role and location are required"
      );
    }

const query = `${role} ${skills.join(" ")} ${jobType} jobs in ${locations.join(", ")}`;


    const response = await axios.get(
      "https://jsearch.p.rapidapi.com/search",
      {
        params: {
          query,
          page: 1,
          num_pages: 1,
          country: "us",
          date_posted: "all"
        },
       headers: {
         "x-rapidapi-host": functions.config().rapidapi.host,
         "x-rapidapi-key": functions.config().rapidapi.key
       }

      }
    );

    return response.data.data.map(job => ({
      title: job.job_title,
      company: job.employer_name,
      location: job.job_city || job.job_country,
      type: job.job_employment_type,
      applyUrl: job.job_apply_link,
      postedAt: job.job_posted_at_datetime_utc
    }));

  } catch (error) {
    console.error("Job search failed:", error);
    throw new functions.https.HttpsError(
      "internal",
      "Failed to fetch jobs"
    );
  }
});
