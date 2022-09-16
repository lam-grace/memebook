import React, {useState} from "react";
import Form from 'react-bootstrap/Form';
import {Button, ButtonGroup} from "react-bootstrap";
import UploadFiles from "../upload/UploadFiles";
import {useNavigate, useParams} from "react-router-dom";
import {deleteUser, getAllUsers, getUserDetail, saveUserDetail, logoutUser} from "../../api/UserApi";
import WebImage from "../webimage/WebImage";
import Modal from "react-bootstrap/Modal";
import {LinkContainer} from "react-router-bootstrap";


const EditProfile = () => {
    let { userId } = useParams();

    const defaultErrors = {
        username: '',
        password: '',
        confirmPassword: '',
    };

    const defaultValid = {
        username: false,
        password: false,
        confirmPassword: false,
    };

    const [user, setUser] = useState({});
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [callingDetail, setCalling] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [showModalDelete, setShowModalDelete] = useState(false);
    const [showModalUpdate, setShowModalUpdate] = useState(false);
    const [username, setUserName] = useState('');
    const [errors, setErrors] = useState(defaultErrors);
    const [validItems, setValidItems] = useState(defaultValid);
    const [showInvalidUsernameModal, setInvalidUsernameModal] = useState(false);
    const navigate = useNavigate();

    const handleUpdateDetail = (event) => {
        event.preventDefault();
        const updateDetail = async () => {
            saveUserDetail(user).then(data=>{
                if(!data.hasOwnProperty("errorMessages")){
                    setShowModalUpdate(true);
                    setUser(data);
                    // navigate({pathname: `/viewProfile/${userId}`});
                } else {
                    console.log(data.errorMessages);
                    setInvalidUsernameModal(true)
                }
            });
        }
        updateDetail();
    };

    const getDetail = () => {
        const userDetail = async () => {
            if (!callingDetail) {
                setCalling(true);
                getUserDetail(userId).then(data => {
                    setUser(data);
                });
            }
        }
        userDetail();
    };
    getDetail();

    const handleDeleteModal = () => {
        setShowModal(true);
    };

    const handleDelete = () => {
        const deletePerson = async () => {
            deleteUser(userId).then(data => {
                logoutUser().then(data => {
                    setShowModal(false);
                    setShowModalDelete(true);
                });
            });
        }
        deletePerson();
    }

    const handleCancel = () => {
        navigate(-1);
    }

    const handleCancelDelete = () => {
        setShowModal(false);
    };

    const refresh = () => {
        window.location.reload(false);
    };

    const handleUserInput = (event) => {
        event.preventDefault();
        const {id, value} = event.target;
        // switch (id) {
            // case 'username':
                setUserName(value);
                user.username = value;
                if (event.target.validationMessage == null) {
                    errors.username = (username.length < 5) ? 'Username must be at least 5 characters' : '';
                } else {
                    errors.username = event.target.validationMessage;
                }
                validItems.username = (errors.username === '');
        //         break;
        //     case 'password':
        //             if (value == "") {
        //
        //             } else {
            //         setPassword(value);
            //         user.password = value;
            //         errors.password = password.length > 0 ? ((password.length < 5) ? 'Password must be at least 5 characters' : '') : '';
            //         // errors.password = (password.length < 5) ? 'Password must be at least 5 characters' : '';
            //         validItems.password = (errors.password === '');
            //         }

        //         break;
        //     case 'confirmPassword':
        //         setConfirmPassword(value);
        //         user.confirmPassword = value;
        //         errors.confirmPassword = (value === password) ? '' : 'Passwords must match'
        //         validItems.confirmPassword = (errors.confirmPassword === '');
        //         break;
        //     default:
        //         break;
        // }
        setUser(user);
        setErrors(errors);
        setValidItems(validItems);
    }

    return (
            <>
                {user !== undefined &&
                    <Form  className="form" onSubmit={handleUpdateDetail}>
                        <Form.Group className="mb-3" key="username">
                            <Form.Label htmlFor="username">Username</Form.Label>
                            <Form.Control type="text" id="username" value={username} className="me-2" onChange={handleUserInput}/>
                        </Form.Group>
                        {/*<Form.Group className="mb-3" key="password">*/}
                        {/*    <Form.Label htmlFor="password">Password</Form.Label>*/}
                        {/*    <Form.Control type="text" id="password" className="me-2" onChange={handleUserInput}/>*/}
                        {/*</Form.Group>*/}
                        {/*<Form.Group className="mb-3" key="confirmPassword">*/}
                        {/*    <Form.Label htmlFor="confirmPassword">Password</Form.Label>*/}
                        {/*    <Form.Control type="text" id="confirmPassword" className="me-2" onChange={handleUserInput}/>*/}
                        {/*</Form.Group>*/}
                        {/*{user?.profileImageId > 0 &&*/}
                        {/*    <WebImage cssClass="profileEditPic" imageId={user?.profileImageId} altText={user?.userName}/>*/}
                        {/*}*/}
                        {/*{user?.profileImageId === 0 &&*/}
                        {/*    <UploadFiles parentCallback={uploadedProfilePic}></UploadFiles>*/}
                        {/*}*/}
                        <br/>
                        <ButtonGroup>
                            <Button variant="outline-success" type="submit" onClick={handleUpdateDetail}> Save </Button>
                            <Button variant="danger" onClick={handleDeleteModal}>Delete Account</Button>
                            <Button variant="outline-secondary" onClick={handleCancel}>Cancel</Button>
                        </ButtonGroup>
                    </Form>
                }
                <Modal show={showModal} onHide={handleCancelDelete}>
                    <Modal.Header closeButton>
                        <Modal.Title>Are you sure you want to delete your account?</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Button variant="danger" onClick={handleDelete} type="submit"> Yes I am sure </Button>
                        <Button variant="success" onClick={handleCancelDelete}>No I want to keep my account</Button>
                    </Modal.Body>
                </Modal>

                <Modal show={showModalDelete} onHide={handleCancelDelete}>
                    <Modal.Header closeButton>
                        <Modal.Title>Your account has been deleted</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Button variant="secondary" href="/Home"> Return to Home Page </Button>
                    </Modal.Body>
                </Modal>

                <Modal show={showModalUpdate} onHide={handleCancelDelete}>
                    <Modal.Header closeButton>
                        <Modal.Title>Congratulations!</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        Your profile has been updated
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" href="/Home"> Return to Home Page</Button>
                    </Modal.Footer>
                </Modal>
                <Modal show={showInvalidUsernameModal} onHide={refresh}>
                    <Modal.Header closeButton>
                        <Modal.Title>Oops! Something went wrong.</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        Username already exists! Please try again.
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="primary" onClick={refresh} >Retry</Button>
                    </Modal.Footer>
                </Modal>
            </>

    );
};

export default EditProfile;
