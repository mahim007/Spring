import React from 'react';
import {CookiesProvider} from 'react-cookie';
import './App.css';
import { Route, BrowserRouter, Switch } from 'react-router-dom';
import Home from './component/Home/Home';
import GroupList from './component/GroupList/GroupList';
import GroupEdit from './component/GroupEdit/GroupEdit';

function App() {
    return (
        <CookiesProvider>
            <BrowserRouter>
                <Switch>
                    <Route path="/" exact={true} component={Home} />
                    <Route path="/groups" exact={true} component={GroupList} />
                    <Route path="/groups/:id" component={GroupEdit} />
                </Switch>
            </BrowserRouter>
        </CookiesProvider>
    );
}

export default App;
