import '../../App.css';
import React, { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import * as Icon from 'react-bootstrap-icons';
import Fade from 'react-reveal/Fade';

import {useNavigate, useParams} from "react-router-dom";

import {Button, ButtonGroup} from "react-bootstrap";

import MemeComponent from "../meme/MemeComponent";
import {getUser, getUserDetail, getUserFavourites, getUserMemes} from "../../api/UserApi";


const ViewProfile = () => {
    let { authorId } = useParams();
    const [user, setUser] = useState({});

    useEffect(() => {
        const getAuthor = async () => {
            // await getUserDetail.then(data => {
            //     setUser(data);
            // })
            const data = await getUserDetail(authorId);
            setUser(data);
        }
        getAuthor();
    }, [authorId]);

    const [currentUser, setCurrentUser] = useState({});

    useEffect(() => {
        const getCurrent = async () => {
            const data = await getUser();
            setCurrentUser(data);
        }
        getCurrent();
    }, []);


    const [usersMemes, setUsersMemes] = useState([]);
    useEffect(() => {
        const getProfileMemes = async () => {
            const data = await getUserMemes(authorId);
            setUsersMemes(data);
        }
        getProfileMemes();
    }, [authorId]);


    const [userFavourites, setUserFavourites] = useState([]);
    useEffect(() => {
        const getFavourites = async () => {
            const data = await getUserFavourites(authorId);
            setUserFavourites(data);
        }
        getFavourites();
    }, [authorId]);

    return (
        <>
            <Row>
                <div>
                    <h1 className="p-3 header1">
                        {user?.username}'s Profile
                    </h1>

                    {user?.userId === currentUser?.userId &&
                    <LinkContainer to={{pathname: `/editProfile/${authorId}`}}>
                        <Button variant="outline-primary"> Edit Profile </Button>
                    </LinkContainer>
                    }

                        {/*<div>        Try show the actual persons name at the top   */}
                        {/*    {this.state.usersMemes.map((meme, index) => (*/}
                        {/*        <h1 >{meme.author}'s Profile </h1>*/}
                        {/*    ))}*/}
                        {/*</div>*/}
                </div>
            </Row>

            <Row className="p-3">
                <h2 className='header2'>Memes Created:</h2>
                {usersMemes?.map(meme => (   /*Loops through users memes */
                    <Col key={meme.memeId} md="auto">
                        <Card className="my-2"
                              style={{
                                  width: '470px'
                              }}>
                            <Card.Body >
                                <Card.Link>
                                    <LinkContainer to={{pathname: `/memeDetail/${meme.memeId}`}} >
                                        <Nav.Link><MemeComponent memeId={meme.memeId} cssClass="meme" altText="image"/></Nav.Link>
                                    </LinkContainer>
                                </Card.Link>

                                <Card.Text>
                                    <div>
                                        <span className='float-start ps-3 pt-2'>Artist: {meme.author} </span>
                                        <span className='float-end pe-3 pt-2'><Icon.HandThumbsUpFill className="icon-colour"/> Likes: {meme.popularity}</span>
                                    </div>
                                </Card.Text>


                            </Card.Body>
                        </Card>
                    </Col>
                    ))}
            </Row>

            <Row className="p-3">
                <h2 className='header2'>Favourite Memes:</h2>
                {userFavourites?.map(Favourite => (   /*Loops through users memes */
                    <Col key={Favourite.memeId} md="auto">
                        <Fade clear>
                        <Card className="my-2"
                              style={{
                                  width: '470px'
                              }}>
                            <Card.Body >
                                <Card.Link>
                                    <LinkContainer to={{pathname: `/memeDetail/${Favourite.memeId}`}} >
                                        <Nav.Link><MemeComponent memeId={Favourite.memeId} cssClass="meme" altText="image"/></Nav.Link>
                                    </LinkContainer>
                                </Card.Link>

                                <Card.Text>
                                    <div>
                                        <span className='float-start ps-3 pt-2'>Artist: {Favourite.author} </span>
                                        <span className='float-end pe-3 pt-2'><Icon.HandThumbsUpFill className="icon-colour"/> Likes: {Favourite.popularity}</span>
                                    </div>
                                </Card.Text>
                            </Card.Body>
                        </Card>
                        </Fade>
                    </Col>
                ))}
            </Row>
        </>

    );
}
export default ViewProfile;
