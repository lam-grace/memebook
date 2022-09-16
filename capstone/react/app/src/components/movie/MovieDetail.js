import React, {useEffect, useState} from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {getMovieDetail, saveMovieDetail} from "../../api/MovieApi";
import {useParams} from "react-router-dom";
import {ListGroup, ListGroupItem} from "reactstrap";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import {Button, ButtonGroup} from "react-bootstrap";
import {Routes, Route, useNavigate} from 'react-router-dom';
import * as Icon from 'react-bootstrap-icons';
import {getUser} from "../../api/UserApi";
// import { saveAs } from 'file-saver'


import Moment from 'moment'


const MovieDetail = () => {
  let { movieId } = useParams();
  const [movieDetail, setMovieDetail] = useState({});
  const [callingDetail, setCalling] = useState(false);
  const navigate = useNavigate();

  const [movieOverview, setMovieOverview] = useState("");
  const [showMovieDetailModal, setShowMovieDetailModal] = useState(false);



  const handleCancel = () => {
    setShowMovieDetailModal(false);
  };
  const handleUpdateDetail = (event) => {
    if (event !== undefined) event.preventDefault();
    const updateDetail = async () => {
      movieDetail.overview = movieOverview;
      saveMovieDetail(movieDetail).then(data=>{
        setMovieDetail(data);
        setShowMovieDetailModal(false);
      });
    }
    updateDetail();
  };
  const doSetOverview = (event) => {
    setMovieOverview(event.target.value);
  };
  const handleShowModalOn = () => {
    if (movieDetail!==undefined) {
      setMovieOverview(movieDetail.overview);
      setShowMovieDetailModal(true);
    }
  };
  const getDetail = () => {
    const movieDetail = async () => {
      if (!callingDetail) {
        setCalling(true);
        getMovieDetail(movieId).then(data => {
          setMovieDetail(data);
        });
      }
    }
    movieDetail();
  };
  getDetail();


  const navigateUseTemplate = () => {
    // 👇️ navigate to /
    navigate({pathname: `/createMeme/${movieDetail?.id}`});    //MakeMeme with meme id
  };

  const navigateUserProfile = () => {
    // 👇️ navigate to /
    navigate('/ViewProfile');  //  add specific users using their id
  };


  const columns = [
    { label: "Name", accessor: "name", sortable: true, sortbyOrder: "asc" }
  ];
  return (
      <>
        <Row>
          <div>
            <h1>
              Meme Detail:
            </h1>
          </div>
        </Row>
        <Row>
          <Col key="imageCard">
            <Card className="my-2"
                  style={{
                    minWidth: '16rem',
                    maxHeight: '50rem'
                  }}>
              <Card.Img variant="top" src={movieDetail?.posterPath} alt={movieDetail?.title} width="10%" />
              <div className='text-center'>
                <Button variant="outline-primary" className='float-start mx-5'>Like</Button>{' '}
                <Button variant="outline-warning" className='float-none'>Save</Button>{' '}
                <Button variant="outline-danger" className='float-end mx-5'>Report</Button>{' '}
              </div>
            </Card>
          </Col>

          <Col key="titleCard">
            <Card className="my-2"
                  style={{
                    minWidth: '16rem'
                  }}>
              <Card.Body>
                {/*<Card.Title>{movieDetail?.title}</Card.Title>*/}
                {/*<Card.Subtitle className="mb-2 text-muted moveSubtitle"*/}
                {/*               tag="h6">{movieDetail?.tagline}</Card.Subtitle>*/}
                <Card.Link onClick={navigateUserProfile} href="#" /*go to user view memes page */><Icon.PersonFill className="icon-colour"/> User: {/*{userID}? */} </Card.Link>


                <Card.Text>
                    <Icon.CalendarHeartFill className="icon-colour"/>

                  Meme upload date: {/*{uploadDate.format('YYYY-MM-DD')} */}
                </Card.Text>
                <Card.Text>
                  <Icon.HandThumbsUpFill className="icon-colour"/>
                  Likes: {/*{popularity} */}
                </Card.Text>

                <Card.Text>
                  <Icon.TagsFill className="icon-colour"/>
                  Tags:
                </Card.Text>


                <Button variant="outline-secondary" type="submit" onClick={navigateUseTemplate}>Use Template</Button>{' '}


              </Card.Body>
            </Card>
          </Col>
          {/*<Col key="actorsCard">*/}
          {/*  <Card className="my-2"*/}
          {/*        style={{*/}
          {/*          minWidth: '16rem'*/}
          {/*        }}>*/}
          {/*    <Card.Title>Actors</Card.Title>*/}
          {/*    {movieDetail?.actors !== undefined && <ListGroup flush>*/}
          {/*      {movieDetail.actors.map(actor => (*/}
          {/*          <ListGroupItem key={actor.id}>*/}
          {/*            {actor.name}*/}
          {/*          </ListGroupItem>*/}
          {/*      ))}*/}
          {/*    </ListGroup>*/}
          {/*    }*/}
          {/*  </Card>*/}
          {/*</Col>*/}
        </Row>
        <Modal show={showMovieDetailModal} onHide={handleCancel}>
          <Form onSubmit={handleUpdateDetail}>
            <Modal.Header closeButton>
              <Modal.Title>Edit Movie Overview</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Form.Group className="mb-3">
                <Form.Label>Description</Form.Label>
                <Form.Control as="textarea" rows={10}
                              value={movieOverview} onChange={doSetOverview} />
              </Form.Group>
            </Modal.Body>
            <Modal.Footer>
              <ButtonGroup>
                <Button variant="primary" onClick={handleUpdateDetail} type="submit">
                  Save
                </Button>
                <Button variant="secondary" onClick={handleCancel}>
                  Cancel
                </Button>
              </ButtonGroup>
            </Modal.Footer>
          </Form>
        </Modal>
      </>
  );
}
export default MovieDetail;