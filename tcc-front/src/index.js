/* tcc-java */
import React from 'react';
import './index.css';
import * as ServiceWorker from './serviceWorker';
import App from './App';
import ReactDOM from 'react-dom';
import {BrowserRouter} from 'react-router-dom';

ReactDOM.render(
			<BrowserRouter>
				<App/>
			</BrowserRouter>
		, document.getElementById("root"));
ServiceWorker.unregister();
