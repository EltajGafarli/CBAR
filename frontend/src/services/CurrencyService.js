import axios from 'axios';

const API_BASE_URL = 'http://localhost:8088/api/cbar';

export const getCurrencies = async () => {
    const response = await axios.get(API_BASE_URL);
    return response.data;
};

export const convertCurrency = async (currencyRequest) => {
    const response = await axios.post(`${API_BASE_URL}/convert`, currencyRequest);
    return response.data;
};

export const findAllByCodeOrName = async (input) => {
    const response = await axios.get(`${API_BASE_URL}/codename`, { params: { input } });
    return response.data;
};


export const findAllCodes = async () => {
    const response = await axios.get(`${API_BASE_URL}/codes`);
    return response.data;
}