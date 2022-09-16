import React, {useState} from "react";
import Form from 'react-bootstrap/Form';
import {Button, ButtonGroup} from "react-bootstrap";
import {addUserDetail, getUserDetail, saveUserDetail} from "../../api/UserApi";
import WebImage from "../webimage/WebImage";
import Modal from "react-bootstrap/Modal";
import {Routes, Route, useNavigate} from 'react-router-dom';



const RegisterUser = () => {
    const defaultUser = {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'Standard',
        firstName: '',
        lastName: '',
    };

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

    const [user, setUser] = useState(defaultUser)
    const [errors, setErrors] = useState(defaultErrors);
    const [validItems, setValidItems] = useState(defaultValid);

    const [username, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [role, setRole] = useState(false);
    const navigate = useNavigate();


    const [showModal, setShowModal] = useState('');
    const [showInvalidUsernameModal, setInvalidUsernameModal] = useState(false);

    const handleClose = () => setShowModal(false);


    const handleUserInput = (event) => {
        event.preventDefault();
        const {id,value} = event.target;
        switch (id) {
            case 'username':
                setUserName(value);
                user.username = value;
                if (event.target.validationMessage == null) {
                    errors.username = (username.length < 5) ? 'Username must be at least 5 characters' : '';
                } else {
                    errors.username = event.target.validationMessage;
                }
                validItems.username = (errors.username === '');
                break;
            case 'password':
                setPassword(value);
                user.password = value;
                errors.password = (password.length < 5) ? 'Password must be at least 5 characters' : '';
                validItems.password = (errors.password === '');
                break;
            case 'confirmPassword':
                setConfirmPassword(value);
                user.confirmPassword = value;
                errors.confirmPassword = (value === password) ? '' : 'Passwords must match'
                validItems.confirmPassword = (errors.confirmPassword === '');
                break;
            case 'firstName':
                setFirstName(value);
                user.firstName = value;
                break;
            case 'lastName':
                setLastName(value);
                user.lastName = value;
                break;
            case 'role':
                setRole(value);
                user.role = role;
                break;
            default:
                break;
        }
        setUser(user);
        setErrors(errors);
        setValidItems(validItems);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const save = async  () => {
            addUserDetail(user).then(data=>{

                // if(!data.hasOwnProperty("errorMessages")){
                    setUser(data) ;
                    setShowModal(true);
                // } else{
                //     console.log(data.errorMessages);
                //     setInvalidUsernameModal(true)
                // }
            });
        }
        save();
    }

    const navigateUseTemplate = () => {
        // 👇️ navigate to /
        navigate({pathname: `/Home`});    //MakeMeme with meme id
    };

    const refresh = () => {
        window.location.reload(false)

    }

    return (
        <>
            <Modal show={showModal} onHide={navigateUseTemplate}>
                <Modal.Header closeButton>
                    <Modal.Title>Congratulations!</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Thank you for registering!
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={navigateUseTemplate} >Home Page</Button>
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
            <Form  className="container" onSubmit={handleSubmit}>
                <div className="row mt-4">
                    <Form.Group className="col-6" key="username">
                        <Form.Label htmlFor="username">Username</Form.Label>
                        <Form.Control type="text"  id="username" value={username} onChange={handleUserInput} />
                        <Form.Text className="error">{errors.username}</Form.Text>
                    </Form.Group>
                </div>

                <div className="row mt-4">
                    <Form.Group className="col-6" key="password">
                        <Form.Label htmlFor="password">Password</Form.Label>
                        <Form.Control type="password"  id="password" value={password} onChange={handleUserInput}/>
                        <Form.Text className="error">{errors.password}</Form.Text>
                    </Form.Group>
                    <Form.Group className="col-6" key="confirmPassword">
                        <Form.Label htmlFor="confirmPassword">Confirm Password</Form.Label>
                        <Form.Control type="password"  id="confirmPassword" value={confirmPassword} onChange={handleUserInput}/>
                        <Form.Text className="error">{errors.confirmPassword}</Form.Text>
                    </Form.Group>
                </div>

                {/*<div className="row mt-4">*/}
                {/*    <Form.Group className="col-6" key="firstName">*/}
                {/*        <Form.Label htmlFor="firstName">First Name</Form.Label>*/}
                {/*        <Form.Control type="text"  id="firstName" value={firstName} onChange={handleUserInput}/>*/}
                {/*    </Form.Group>*/}
                {/*    <Form.Group className="col-6" key="lastName">*/}
                {/*        <Form.Label htmlFor="lastName">Last Name</Form.Label>*/}
                {/*        <Form.Control type="text"  id="lastName" value={lastName} onChange={handleUserInput}/>*/}
                {/*    </Form.Group>*/}
                {/*</div>*/}
                {/*<div className="row mt-4">*/}
                {/*    <Form.Group className="col-4" key="role">*/}
                {/*        <Form.Label htmlFor="role">Role</Form.Label>*/}
                {/*        <Form.Control*/}
                {/*            as="select"*/}
                {/*            id="role"*/}
                {/*            value={role}*/}
                {/*            onChange={handleUserInput}*/}
                {/*        >*/}
                {/*            <option value="Standard">Standard</option>*/}
                {/*            <option value="Admin">Admin</option>*/}
                {/*        </Form.Control>*/}
                {/*    </Form.Group>*/}
                {/*</div>*/}
                <div className="row mt-4">
                    <Button className="col-2" variant="primary" type="submit"
                             disabled={!(validItems.username && validItems.password && validItems.confirmPassword)}
                    >Save</Button>
                </div>

            </Form>

        </>

    );
};

export default RegisterUser;
