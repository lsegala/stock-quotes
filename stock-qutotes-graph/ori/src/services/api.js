const axios = require('axios');

const API_URL = 'http://localhost:8080';

export const getChart = (params) => axios.get(
    `${API_URL}/quotes?size=1&page=0&sort=timestamp,desc`
);