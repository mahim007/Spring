import React from "react";
import AppNavbar from "../AppNavbar/AppNavbar";
import { Container, Button } from "reactstrap";
import { Link } from "react-router-dom";

const Home = () => {
    return(
        <div>
            <AppNavbar/>
            <Container fluid>
                <Button color="link"><Link to="/groups">Manage Tours</Link></Button>
            </Container>
        </div>
    );
};

export default Home;