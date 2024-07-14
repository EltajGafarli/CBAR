import React, { useState, useEffect } from 'react';
import './CurrencyList.css';
import { getCurrencies, findAllByCodeOrName } from '../services/CurrencyService';

const CurrencyList = () => {
    const [currencies, setCurrencies] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchCurrencies = async () => {
            try {
                const result = await getCurrencies();
                setCurrencies(result);
            } catch (error) {
                console.error("Error fetching currencies", error);
            }
        };
        fetchCurrencies();
    }, []);

    useEffect(() => {
        const filterCurrencies = async () => {
            if (searchTerm) {
                try {
                    const result = await findAllByCodeOrName(searchTerm);
                    setCurrencies(result);
                } catch (error) {
                    console.error("Error fetching filtered currencies", error);
                }
            } else {
                const fetchCurrencies = async () => {
                    try {
                        const result = await getCurrencies();
                        setCurrencies(result);
                    } catch (error) {
                        console.error("Error fetching currencies", error);
                    }
                };
                fetchCurrencies();
            }
        };
        filterCurrencies();
    }, [searchTerm]);

    return (
        <div className="currency-list">
            <input
                type="text"
                placeholder="Search (code, name)"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="search-input"
            />
            <table>
                <thead>
                    <tr>
                        <th>CODE</th>
                        <th>NAME</th>
                        <th>NOMINAL</th>
                        <th>VALUE</th>
                    </tr>
                </thead>
                <tbody>
                    {currencies.map((currency) => (
                        <tr key={currency.code}>
                            <td>{currency.code}</td>
                            <td>{currency.name}</td>
                            <td>{currency.nominal}</td>
                            <td>{currency.value}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default CurrencyList;
