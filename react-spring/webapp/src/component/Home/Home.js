import React, { useState, useEffect } from "react";
import AppNavbar from "../AppNavbar/AppNavbar";
import { Container, Button } from "reactstrap";
import { Link } from "react-router-dom";
import { withCookies } from "react-cookie";

const Home = (props) => {

    const [loading, setLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState(undefined);

    const {cookies} = props;
    const csrfToken = cookies.get('XSRF-TOKEN');

    useEffect(() => {
        const url = '/api/user';
        const config = {
            credentials: 'include'
        };

        fetch(url, config)
        .then(response => response.text())
        .then(response => {
            if (response === '') {
                setIsAuthenticated(false);
            } else {
                setIsAuthenticated(true);
                setUser(JSON.parse(response));
            }
        });
    }, []);

    const login = () => {
        let port = window.location.port ? (':' + window.location.port) : '';
        if (port === ':3000') {
            port = ':8080';
        }

        window.location.href = '//' + window.location.hostname + port + '/private';
    };

    const logout = () => {
        const url = '/api/logout';
        const config = {
            method: 'POST',
            credentials: 'include',
            headers: {
                'X-XSRF-TOKEN': csrfToken
            }
        };

        fetch(url, config)
        .then(response => response.json())
        .then(response => {
            window.location.href = response.logoutUrl + '?id_token_hint=' + response.idToken + '&post_logout_redirect_uri=' + window.location.origin;
        });
    };

const message = user ? <h2>Welcome, {user.name}!</h2> : <p>Please login to manage your Group.</p>
const button = isAuthenticated ? (
    <div>
        <Button color='link'><Link to='/groups'>Manage Group</Link></Button>
        <br />
        <Button color='link' onClick={logout} >Logout</Button>
    </div>
) : <Button color='primary' onClick={login}  >Login</Button>

    return(
        <div>
            <AppNavbar/>
            <Container fluid>
                {message}
                {button}
            </Container>
        </div>
    );
};

export default withCookies(Home);