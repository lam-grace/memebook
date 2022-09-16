import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { LinkContainer } from 'react-router-bootstrap';
import "bootstrap/dist/css/bootstrap.min.css";
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Form from 'react-bootstrap/Form';
import { ButtonGroup, Button } from 'react-bootstrap';
import './App.css';
import Home from './components/home/Home';
import React, { useState, useEffect } from 'react';
import Modal from 'react-bootstrap/Modal';
import {getUser, getUserFromCookie, loginUser, logoutUser} from './api/UserApi';
import SampleModalComponent from './components/modal/SampleModalComponent';
import AuthExample from './components/auth/AuthExample';

import EditProfile from "./components/user/EditProfile";
import UploadImage from "./components/admin/UploadImage";
import MemeDetail from "./components/meme/MemeDetail";
//new imports

import ViewProfile from "./components/user/ViewProfile";
import { ArrowDownCircle, PersonFill } from "react-bootstrap-icons";
import CreateMeme from "./components/meme/CreateMeme";
import ApproveMemes from "./components/admin/ApproveMemes";
import ViewAllUsers from "./components/admin/ViewAllUsers";
import RegisterUser from "./components/user/RegisterUser";

//meme template stuff
import MemeTemplate from "./components/meme/MemeTemplates";
import {getAllImages} from "./api/ImageApi";

//meme imports
import MemeGrid from "./components/meme/MemeGrid";
import {getAllMemes} from "./api/MemeApi";


window.ADMIN = "Admin";
window.VIP = "Vip";
window.STANDARD = "Standard";
window.GUEST = "GUEST";

function App() {

  let defaultUser = {
    'firstName': '',
    'lastName': '',
    'name': '',
    'token': '',
    'role': 'GUEST'
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

  const [user, setUser] = useState(defaultUser);

  const [showModal, setShowModal] = useState(false);

  const [showLoginErrorModel, setLoginErrorModel] = useState(false);
  const [loginError, setLoginError] = useState(false);


  const handleShowModalOn = () => {
    setShowModal(true);
  };

  const handleShowLoginErrorModel = () => {
    setLoginErrorModel(true);
  };

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  // const [errors, setErrors] = useState(defaultErrors);
  // const [validItems, setValidItems] = useState(defaultValid);


  const doSetUsername = (event) => {
    setUsername(event.target.value);
  };
  const doSetPassword = (event) => {
    setPassword(event.target.value);
  };

  const handleLogin = (event) => {
    // validate
    if (event !== undefined) event.preventDefault();
    const login = async () => {
      loginUser({ 'username': username, 'password': password }).then(data=>{
        if (data !== undefined){
          setPassword('');
          setShowModal(false);
          setUser(data);
          // setLoginErrorModel(true);
        } else {
          setLoginError(true);
          setLoginErrorModel(true);
        }

      });
    }
    login();

  };

  const handleRegister = () => {
    setPassword('');
    setShowModal(false);
  };

  const handleLogout = () => {
    const logout = async () => {
      logoutUser().then(data=>{
        setUser(data);
        setPassword('');
        setShowModal(false);
      });
    }
    logout();

  };

  const handleCancel = () => {
    setPassword('');
    setShowModal(false);
  };

  const handleErrorCancel = () => {
    setLoginErrorModel(false);
    setLoginError(false);
    setUsername('')
    setPassword('');

  }

  useEffect(() => {
    const getUserFromCookie = async () => {
      getUser().then( user=> {
            setUser(user);
          }
      );
    }
    getUserFromCookie();
  }, []);

  return (
    <div className="wrapper">
      <Router>
        <Nav className="nav container-fluid">
          <Navbar bg="#83C5BE" variant="light" expand="lg">
            <Container>
              <Navbar.Brand>
                <img
                  // src={`${process.env.PUBLIC_URL}/images/${imageVarName}.png}
                  src="/images/movie.png"
                  width="30"
                  height="30"
                  className="d-inline-block align-top"
                  alt="Movie Logo"
                  onClick={handleShowModalOn}
                />
              </Navbar.Brand>
              <Navbar.Toggle aria-controls="basic-navbar-nav" />
              <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="justify-content-center">
                  <LinkContainer to="/home">
                    <Nav.Link>Home</Nav.Link>
                  </LinkContainer>
                  {/*<LinkContainer to="/modalcomponent">*/}
                  {/*  <Nav.Link>Modal</Nav.Link>*/}
                  {/*</LinkContainer>*/}
                  {/*<LinkContainer to="/auth">*/}
                  {/*  <Nav.Link>Auth</Nav.Link>*/}
                  {/*</LinkContainer>*/}
                  {/*<LinkContainer to="/hello">*/}
                  {/*  <Nav.Link>Say Hello</Nav.Link>*/}
                  {/*</LinkContainer>*/}

                  {(user.role === window.STANDARD || user.role === window.ADMIN|| user.role === window.GUEST) &&
                  <LinkContainer to="/memeGrid">
                    <Nav.Link>View Memes</Nav.Link>
                  </LinkContainer>
                  }

                  {(user.role === window.STANDARD || user.role === window.ADMIN) &&
                  <LinkContainer to="/memeTemplate">
                    <Nav.Link>Make Memes</Nav.Link>
                  </LinkContainer>
                  }

                  {user.role === window.ADMIN &&
                  <LinkContainer to="/approveMemes" className=''>
                    <Nav.Link> Approve Memes </Nav.Link>
                  </LinkContainer>

                  }

                  {user.role === window.ADMIN &&
                  <LinkContainer to="/viewAllUsers" className=''>
                    <Nav.Link> All Users </Nav.Link>
                  </LinkContainer>

                  }

                  {user.role === window.ADMIN &&
                    <LinkContainer to="/uploadImage" className=''>
                    <Nav.Link> Upload Image </Nav.Link>
                    </LinkContainer>

                  }

                  {(user.role === window.STANDARD || user.role === window.ADMIN) &&
                  <LinkContainer to={`/viewProfile/${user.userId}`} className='float-end'>
                    <Nav.Link> <PersonFill/> </Nav.Link>
                  </LinkContainer>

                  }

                </Nav>

              </Navbar.Collapse>
            </Container>

          </Navbar>
        </Nav>
        {user.userId === 0 &&
          <Modal show={showModal} onHide={handleCancel}>
            <Form onSubmit={handleLogin}>
              <Modal.Header closeButton>
                <Modal.Title>Log In</Modal.Title>
              </Modal.Header>
              <Modal.Body>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label>Username</Form.Label>
                  <Form.Control type="email" placeholder="Enter username"
                    value={username} /*onChange={handleUserInput}*/ onChange={doSetUsername} />
                  {/*<Form.Text className="error">{errors.username}</Form.Text>*/}
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicPassword">
                  <Form.Label>Password</Form.Label>
                  <Form.Control type="password" placeholder="Password"
                    value={password} /*onChange={handleUserInput}*/ onChange={doSetPassword} />
                  {/*<Form.Text className="error">{errors.password}</Form.Text>*/}
                </Form.Group>
              </Modal.Body>
              <Modal.Footer>
                <ButtonGroup>
                  <Button variant="primary" onClick={handleRegister} href="/registerUser">
                    Register
                  </Button>
                  <Button variant="success" onClick={handleLogin} type="submit" href="/Home">
                    Login
                  </Button>
                  <Button variant="secondary" onClick={handleCancel}>
                    Cancel
                  </Button>
                </ButtonGroup>
              </Modal.Footer>
            </Form>
          </Modal>
        }
        {loginError === true &&
          <Modal show={showLoginErrorModel}>
            <Form>
              <Modal.Body>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label className="error-color">Username or Password does not match.</Form.Label>
                  {/*<Form.Text className="error">Username or Password does not match</Form.Text>*/}
                </Form.Group>
              </Modal.Body>
              <Modal.Footer>
                  <Button variant="secondary" onClick={handleErrorCancel}>
                    Retry
                  </Button>
              </Modal.Footer>
            </Form>
          </Modal>
        }

        {user.userId !== 0 &&
          <Modal show={showModal} onHide={handleCancel}>
            <Form>
              <Modal.Header closeButton>
                <Modal.Title>Log Out</Modal.Title>
              </Modal.Header>
              <Modal.Body>
                <ButtonGroup>
                  <Button variant="primary" onClick={handleLogout}  type="submit" href="/Home">
                    Logout
                  </Button>
                  <Button variant="secondary" onClick={handleCancel}>
                    Cancel
                  </Button>
                </ButtonGroup>
              </Modal.Body>
            </Form>
          </Modal>
        }

        <div>
          <Routes>
            <Route
              path="/" exact
              element={<Home />} />
            <Route
              path="/home" exact
              element={<Home />} />
            <Route
              path="/modalcomponent" exact
              element={<SampleModalComponent />} />
              <Route
                path="/auth" exact
                element={<AuthExample />} />
            <Route
                path="/memeGrid"
                element={<MemeGrid />} />
            <Route
                path="/editProfile/:userId"
                element={<EditProfile  />} />
            <Route
              path="/memeTemplate"
              element={<MemeTemplate />}/>
            <Route
                path="/approveMemes"
                element={<ApproveMemes />}/>
            <Route
                path="/viewAllUsers"
                element={<ViewAllUsers />}/>
            <Route
                path="/uploadImage"
                element={<UploadImage/>}/>
            <Route
                path="/viewProfile/:authorId"
                element={<ViewProfile />}/>
            <Route
                path="/createMeme/:imageId"
                element={<CreateMeme  />} />
            <Route
                path="/memeDetail/:memeId"
                element={<MemeDetail  />} />
            <Route
                path="/registerUser"
                element={<RegisterUser />} />
            <Route
                path="/assignTag/:tagId"
                element={<CreateMeme />} />

          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;