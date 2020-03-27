import React from 'react';
import './App.css';
import { Route, BrowserRouter, Switch } from 'react-router-dom';
import Home from './component/Home/Home';
import GroupList from './component/GroupList/GroupList';
import GroupEdit from './component/GroupEdit/GroupEdit';

function App() {
    return (
        <BrowserRouter>
            <Switch>
                <Route path="/" exact={true} component={Home} />
                <Route path="/groups" exact={true} component={GroupList} />
                <Route path="/groups/:id" component={GroupEdit} />
            </Switch>
        </BrowserRouter>
    );
}

export default App;
