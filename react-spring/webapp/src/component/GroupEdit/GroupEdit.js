import React, { useState, useEffect } from 'react';
import { withRouter, Link } from 'react-router-dom';
import AppNavbar from '../AppNavbar/AppNavbar';
import { Container, Form, FormGroup, Label, Input, Button } from 'reactstrap';

const GroupEdit = (props) => {
    console.log(props);
    const emptyData = {
        name: '',
        address: '',
        city: '',
        stateOrProvince: '',
        country: '',
        postalCode: ''
    };

    const [item, setItem] = useState(emptyData);

    const handleChange = (event) => {
        console.log(event);
        const name = event.target.name;
        const value = event.target.value;
        const newItem = { ...item };
        newItem[name] = value;
        setItem(newItem);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const url = `/api/groups/${item.id}`;
        const config = {
            method: item.id ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        };
        fetch(url, config);
        props.history.push('/groups');
    };

    useEffect(() => {
        if (props.match.params.id !== 'new') {
            fetch(`/api/groups/${props.match.params.id}`)
                .then(response => response.json())
                .then(data => {
                    setItem(data);
                    console.log(data);
                });
        }
    }, [props.match.params.id]);

    const title = <h2>{item.id ? 'Edit Group' : 'Add Group'}</h2>

    return (
        <div>
            <AppNavbar />
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type='text' name='name' id='name' value={item.name || ''} onChange={handleChange} autoComplete='name' />
                    </FormGroup>
                    <FormGroup>
                        <Label for="address">Address</Label>
                        <Input type='text' name='address' id='address' value={item.address || ''} onChange={handleChange} autoComplete='address-level1' />
                    </FormGroup>
                    <FormGroup>
                        <Label for="city">City</Label>
                        <Input type='text' name='city' id='city' value={item.city || ''} onChange={handleChange} autoComplete='address-level1' />
                    </FormGroup>
                    <div className="row">
                        <FormGroup className="col-md-4 mb-3">
                            <Label for="stateOrProvince">State/Province</Label>
                            <Input type='text' name='stateOrProvince' id='stateOrProvince' value={item.stateOrProvince || ''} onChange={handleChange} autoComplete='address-level1' />
                        </FormGroup>
                        <FormGroup className="col-md-5 mb-3">
                            <Label for="country">Country</Label>
                            <Input type='text' name='country' id='country' value={item.country || ''} onChange={handleChange} autoComplete='address-level1' />
                        </FormGroup>
                        <FormGroup className="col-md-3 mb-3">
                            <Label for="postalCode">Postal Code</Label>
                            <Input type='text' name='postalCode' id='postalCode' value={item.postalCode || ''} onChange={handleChange} autoComplete='address-level1' />
                        </FormGroup>
                    </div>
                    <FormGroup>
                        <Button color='primary' type='submit'>Save</Button>{' '}
                        <Button color='secondary' tag={Link} to='/groups'>Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
};

export default withRouter(GroupEdit);