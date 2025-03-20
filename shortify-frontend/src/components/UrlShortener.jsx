import React, { useState, useEffect } from 'react';
import './UrlShortener.css';

function UrlShortener({ token }) {
  const [originalUrl, setOriginalUrl] = useState('');
  const [shortenedUrls, setShortenedUrls] = useState([]);
  const [message, setMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    fetchUrls();
  }, [token]);

  const fetchUrls = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/urls', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        const data = await response.json();
        setShortenedUrls(data);
      }
    } catch (error) {
      console.error('Error fetching URLs:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setMessage('');

    try {
      const response = await fetch('http://localhost:8080/api/urls', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ originalUrl }),
      });

      const data = await response.json();

      if (response.ok) {
        setMessage('URL successfully shortened!');
        setOriginalUrl('');
        setShortenedUrls([data, ...shortenedUrls]);
      } else {
        setMessage(`Error: ${data.message || 'Failed to shorten URL'}`);
      }
    } catch (error) {
      setMessage(`Error: ${error.message}`);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="url-shortener-container">
      <h2>Shorten a URL</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="originalUrl">Enter URL to shorten:</label>
          <input
            type="url"
            id="originalUrl"
            value={originalUrl}
            onChange={(e) => setOriginalUrl(e.target.value)}
            placeholder="https://example.com"
            required
          />
        </div>
        <button type="submit" disabled={isLoading}>
          {isLoading ? 'Shortening...' : 'Shorten URL'}
        </button>
      </form>
      {message && 
        <div className="message">
          {message}
          {message.includes('successfully') && (
            <div className="copy-section">
              <p>Your shortened URL is ready to use!</p>
              {shortenedUrls.length > 0 && (
                <input 
                  type="text" 
                  value={shortenedUrls[0].shortUrl} 
                  readOnly 
                  onClick={(e) => {
                    e.target.select();
                    navigator.clipboard.writeText(e.target.value);
                    setMessage('URL copied to clipboard!');
                  }}
                  className="copy-input"
                />
              )}
            </div>
          )}
        </div>
      }

      {shortenedUrls.length > 0 && (
        <div className="urls-list">
          <h3>Your Shortened URLs</h3>
          <table>
            <thead>
              <tr>
                <th>Original URL</th>
                <th>Short URL</th>
                
                <th>Created</th>
                <th>Expires</th>
              </tr>
            </thead>
            <tbody>
              {shortenedUrls.map((url, index) => (
                <tr key={index}>
                  <td className="original-url">
                    <a href={url.originalUrl} target="_blank" rel="noopener noreferrer">
                      {url.originalUrl.length > 30
                        ? url.originalUrl.substring(0, 30) + '...'
                        : url.originalUrl}
                    </a>
                  </td>
                  <td>
                    <a href={url.shortUrl} target="_blank" rel="noopener noreferrer">
                      {url.shortUrl}
                    </a>
                  </td>
                  
                  <td>{new Date(url.createdAt).toLocaleDateString()}</td>
                  <td>{new Date(url.expiresAt).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default UrlShortener;