import Table from "../table/Table";
import React, {useEffect, useState} from "react";
import Form from 'react-bootstrap/Form';
import {Button, ButtonGroup} from "react-bootstrap";
import {getUserList, getAllUsers, getUserDetail, deleteUser, upgradeUserToAdmin, downgradeAdminToUser, getUser} from "../../api/UserApi";
import {TableBody} from "../table/TableBody";
import {TableHead} from "../table/TableHead";
import EditUserModal from "./EditUserModal";
import Modal from "react-bootstrap/Modal";


const ViewAllUsers = () => {
    const [searchValue, setSearchValue] = useState("");
    const [numberOfResults, setNumberOfRecords] = useState(0);
    const [isSearching, setIsSearching] = useState(false);
    const [userList, setUserList] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [userRole, setUserRole] = useState([]);
    const [user, setUser] = useState({});
    const [currentUser, setCurrentUser] = useState({});

    useEffect(() => {
        const getUsers = async () => {
            const data = await getAllUsers();
            setUserList(data);
            setNumberOfRecords(data.length);
        }
        getUsers();
    }, []);

    useEffect(() => {
        const getAuthor = async () => {
            getUser().then(data => {
                setCurrentUser(data);
            })
        }
        getAuthor();
    }, []);

    const handleEditClick = (event) => {
        event.preventDefault();
        console.log("clicked:" + event.target.id);
        getUserDetail(event.target.id).then(data => {
            setUser(data);
            setUserRole(data.role);
            setShowModal(true);
        });
    }
    // const doSetSearchValue = (event) => {
    //     setSearchValue(event.target.value);
    // };
    // const handleSearch = (event) => {
    //     if (event !== undefined) event.preventDefault();
    //     if (!isSearching) {
    //         setIsSearching(true);
    //         const search = async () => {
    //             getUserList(searchValue).then(data => {
    //                 setUserList(data);
    //                 setNumberOfRecords(data.length);
    //                 setIsSearching(false);
    //             });
    //         }
    //         search();
    //     }
    // };

    const makeAdmin = () => {
        upgradeUserToAdmin(user.userId).then(data => {
            setShowModal(false);
            getAllUsers().then(data => {
                setUserList(data);
            });
        })
    };

    const makeStandard = () => {
        downgradeAdminToUser(user.userId).then(data => {
            setShowModal(false);
            getAllUsers().then(data => {
                setUserList(data);
            });
        })
    };

    const handleCancel = () => {
        setShowModal(false);
    };

    const handleDelete = (event) => {
        const deletePerson = async () => {
            deleteUser(user.userId).then(data => {
                setShowModal(false);
                getAllUsers().then(data => {
                    setUserList(data);
                });
        });
        }
        deletePerson();
    };


    const columns = [
        { label: "ID", accessor: "userId", sortable: true, sortbyOrder: "desc" },
        { label: "User", accessor: "username", sortable: true },
        { label: "Role", accessor: "role", sortable: true },
        { label: "Edit", accessor: "userId", sortable: false,  callback: {handleEditClick}, type:"button"}
    ];


    return (

        <Form >
            {/*<Form.Control*/}
            {/*    type="search"*/}
            {/*    placeholder="Search"*/}
            {/*    className="me-2"*/}
            {/*    aria-label="Search"*/}
            {/*    value={searchValue}*/}
            {/*    // onChange={doSetSearchValue}*/}
            {/*/>*/}
            {/*<Button variant="outline-success" onClick={} type="submit">Search</Button>*/}


            <div className="table_container">

                <h1>Sortable Users Table, {numberOfResults} users</h1>
                <Table
                    caption=""
                    data={userList}
                    columns={columns}
                    editClick={handleEditClick}
                />
            </div>

            <Modal show={showModal} onHide={handleCancel}>
                <Modal.Header closeButton>
                    <Modal.Title>Edit Users</Modal.Title>
                </Modal.Header>
                    <Modal.Body>
                        <p>User: {user.username}</p>
                <ButtonGroup>
                    {userRole === window.STANDARD ?
                        <Button variant="primary" onClick={makeAdmin} type="submit">
                            Make Admin
                        </Button> : <>
                        {user.userId !== currentUser.userId ?
                        <Button variant="primary" onClick={makeStandard} type="submit">
                            Make Standard
                        </Button> :
                            null
                        } </>
                    }
                    {user.userId !== currentUser.userId &&
                    <Button variant="danger" onClick={handleDelete} type="submit">
                        Delete User
                    </Button> }
                    <Button variant="secondary" onClick={handleCancel}>
                        Cancel
                    </Button>

                </ButtonGroup>
                </Modal.Body>
            </Modal>
        </Form>

    );
};

export default ViewAllUsers;
