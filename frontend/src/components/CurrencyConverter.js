import React, { useState, useEffect } from 'react';
import './CurrencyConverter.css';
import { convertCurrency, findAllCodes } from '../services/CurrencyService';

const CurrencyConverter = () => {
    const [actualAmount, setActualAmount] = useState('');
    const [sourceCode, setSourceCode] = useState('USD');
    const [targetCode, setTargetCode] = useState('AZN');
    const [convertedAmount, setConvertedAmount] = useState(null);
    const [error, setError] = useState(null);
    const [codes, setCodes] = useState([]);

    useEffect(() => {
        const fetchCodes = async () => {
            try {
                const result = await findAllCodes();
                setCodes(result);
            } catch (error) {
                console.error("Error fetching codes", error);
            }
        };
        fetchCodes();
    }, []);

    const handleConvert = async () => {
        const currencyRequest = { actualAmount, sourceCode, targetCode };
        try {
            const result = await convertCurrency(currencyRequest);
            setConvertedAmount(result.amount);
            setError(null);
        } catch (error) {
            setError("Failed to convert currency. Please try again.");
        }
    };

    return (
        <div className="currency-converter">
            <div className="converter-row">
                <input
                    type="number"
                    value={actualAmount}
                    onChange={(e) => setActualAmount(e.target.value)}
                    placeholder="Amount"
                />
                <select value={sourceCode} onChange={(e) => setSourceCode(e.target.value)} >
                    {codes.map(code => (
                        <option key={code} value={code}>{code}</option>
                    ))}
                </select>
                <span className="arrow">→</span>
                <input
                    type="text"
                    value={convertedAmount || ''}
                    readOnly
                    placeholder="Converted Amount"
                />
                <select value={targetCode} onChange={(e) => setTargetCode(e.target.value)}>
                    {codes.map(code => (
                        <option key={code} value={code}>{code}</option>
                    ))}
                </select>
            </div>
            <button onClick={handleConvert}>Convert</button>
            {error && <div className="error-message">{error}</div>}
        </div>
    );
};

export default CurrencyConverter;
