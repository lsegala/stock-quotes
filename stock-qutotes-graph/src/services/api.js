const axios = require('axios');

const API_URL = 'http://localhost:8080';

export const getChart = async (params) => axios.get(
    `${API_URL}/last-quote`
);