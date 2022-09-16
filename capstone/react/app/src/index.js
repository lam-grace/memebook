import React from 'react';
import ReactDOM from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';


function setCsrfToken(){
  const url = `/api/csrf`;
  return fetch(url, {
      mode: 'cors',
      credentials: 'same-origin',
      method: 'GET',
      headers: {'Content-Type': 'application/json'}
  } ).then(response => {
        window.CSRF_TOKEN_HEADER =  response.headers.get("x-csrf-token");
  });
}

const runCsrfTokenSetup = async () => {
  await setCsrfToken();
}
runCsrfTokenSetup().then(() => {console.log("CSRF Token: " + window.CSRF_TOKEN_HEADER)});

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // <React.StrictMode>
    <App />
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
