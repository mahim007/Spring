import React, { useState, useEffect } from 'react';
import { ButtonGroup, Button, Container, Table } from 'reactstrap';
import { Link, withRouter } from 'react-router-dom';
import AppNavbar from '../AppNavbar/AppNavbar';
import { Cookies, withCookies } from 'react-cookie';
import { instanceOf } from 'prop-types';

function GroupList(props) {

    const propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    const [loading, setLoading] = useState(true);
    const [groups, setGroups] = useState([]);

    const { cookies } = props;
    const csrfToken = cookies.get('XSRF-TOKEN');

    useEffect(() => {
        const url = "/api/groups";
        const config = {
            credentials: 'include'
        };

        fetch(url, config)
            .then(response => response.json())
            .then(data => {
                setLoading(false);
                setGroups(data);
            })
            .catch(() => props.history.push('/'));
    }, []);

    const remove = (id) => {
        const url = `/api/groups/${id}`;
        const config = {
            method: 'DELETE',
            credentials: 'include',
            headers: {
                'X-XSRF-TOKEN': csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        fetch(url, config)
            .then(() => {
                const updatedGroup = groups.filter((group) => group.id !== id);
                setGroups(updatedGroup);
            });
    }

    if (loading) {
        return <p>Loading...</p>
    }

    const groupList = groups.map((group) => {
        const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince}`;
        return (
            <tr key={group.id}>
                <td style={{ whiteSpace: 'nowrap' }}>{group.name}</td>
                <td>{address}</td>
                <td>{group.events.map(event => {
                    return (
                        <div key={event.id}>{new Intl.DateTimeFormat('en-US', {
                            year: 'numeric',
                            month: 'long',
                            day: '2-digit'
                        }).format(new Date(event.date))}: {event.title}</div>
                    )
                })}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/groups/" + group.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => remove(group.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        );
    });

    return (
        <div>
            <AppNavbar />
            <Container fluid>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/groups/new">Add Group</Button>
                </div>
                <h3>Group Management</h3>
                <Table className="mt-4">
                    <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Location</th>
                            <th>Events</th>
                            <th width="10%">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {groupList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
}

export default withCookies(withRouter(GroupList));