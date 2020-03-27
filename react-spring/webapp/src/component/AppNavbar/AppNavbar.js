import React, {useState} from "react";
import { Navbar, NavbarBrand, NavbarToggler, Collapse, Nav, NavItem, NavLink } from "reactstrap";
import { Link } from "react-router-dom";

function AppNavbar() {
    const [isOpen, setIsOpen] = useState(false);
    
    const toggle = () => {
        setIsOpen(!isOpen);
    }

    return(
        <Navbar color="dark" dark expand="md">
            <NavbarBrand tag={Link} to="/">Home</NavbarBrand>
            <NavbarToggler onClick={() => toggle()}/>
            <Collapse isOpen={isOpen} navbar>
                <Nav className="ml-auto" navbar>
                    <NavItem>
                        <NavLink href="https://www.facebook.com/AshrafulIslamMahim007">facebook</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="https://github.com/mahim007">github</NavLink>
                    </NavItem>
                </Nav>
            </Collapse>
        </Navbar>
    );
}

export default AppNavbar;