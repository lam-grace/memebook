
import '../../App.css';
import React, { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {Button} from "reactstrap";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {getMovieList} from "../../api/MovieApi";
import {getActorList} from "../../api/ActorApi";
import {getUserMemes} from "../../api/UserApi";
import {getTopMemes} from "../../api/MemeApi";
import MemeComponent from "../meme/MemeComponent";
import * as Icon from 'react-bootstrap-icons';
import Fade from 'react-reveal/Fade';

const Home = () => {

    const [topFive, setTopFive] = useState([]);
    useEffect(() => {
        const getMostPopular = async () => {
            const data = await getTopMemes();
            setTopFive(data);
        }
        getMostPopular();
    }, []);

    return (
        <>
            <Fade bottom>
            <div className='home-div'>
            <h1 className="home-header">  Welcome to MemeBook!  </h1>
            </div>
            </Fade>
            <Fade right>
        <Row className='mx-2'>
            <h2 className='header2'>Top Memes:</h2>
            {topFive.map(five => (    //Change to memes.map(meme =>
                <Col className="home-memes" md="auto">

                    <Card className="my-2"
                          style={{
                              width: '470px'
                          }}>
                        <Card.Body>
                        <Card.Link>
                            <LinkContainer to={{pathname: `/memeDetail/${five.memeId}`}} >
                                <Nav.Link><MemeComponent memeId={five.memeId} cssClass="meme" altText="image"/></Nav.Link>
                            </LinkContainer>
                        </Card.Link>
                            <Card.Text>
                                <div>
                                    <span className='float-start ps-3 pt-2'>Artist: {five.author}</span>
                                    <span className='float-end pe-3 pt-2'><Icon.HandThumbsUpFill className="icon-colour"/> Likes: {five.popularity}</span>
                                </div>
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            ))}
        </Row>
            </Fade>
        </>

    );
}
export default Home;