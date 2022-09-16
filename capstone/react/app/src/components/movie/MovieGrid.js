import React, { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {Button} from "reactstrap";
import Form from "react-bootstrap/Form";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {getMovieList, saveMovieDetail} from "../../api/MovieApi";
import {getPopularMemeList, getRecentMemeList} from "../../api/MemeApi"
import Dropdown from 'react-bootstrap/Dropdown';
import * as Icon from 'react-bootstrap-icons';


const MovieGrid = ({movies}) => {

  const [movieList, setMovieList] = useState(movies);
  const [searchValue, setSearchValue] = useState("");
  const [numberOfResults, setNumberOfRecords] = useState(0);
  const doSetSearchValue = (event) => {
    setSearchValue(event.target.value);
  };
  const handleSearch = (event) => {
    if (event !== undefined) event.preventDefault();
    const search = async () => {
      getMovieList(searchValue).then(data=>{
        console.log("Found:" + data.length + " records");
        setMovieList(data);
        setNumberOfRecords(data.length);
      });
    }
    search();
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      console.log('do validate')
    }
  }

  const handleSortPopular = (event) => {
    if (event !== undefined) event.preventDefault();
    const sortPopular = async () => {
      // movieDetail.overview = movieOverview;
      getPopularMemeList().then(data=>{
        setMovieList(data);
        // setShowMovieDetailModal(false);
      });
    }
    sortPopular();
  }

  const handleSortRecent = (event) => {
    if (event !== undefined) event.preventDefault();
    const sortRecent = async () => {
      // movieDetail.overview = movieOverview;
      getRecentMemeList().then(data=>{
        setMovieList(data);
      });
    }
    sortRecent();
  }

  return (
  <Form onSubmit={handleSearch}>
    <Row className="mb-1">
      <Col sm={9}>
        <Dropdown>
          <Dropdown.Toggle className="sortButton btn-light" id="dropdown-basic">
            Sort By
          </Dropdown.Toggle>

          <Dropdown.Menu>
            <Dropdown.Item href="#/action-1" onClick={handleSortPopular}>Most Popular</Dropdown.Item>
            <Dropdown.Item href="#/action-2" onClick={handleSortRecent}>Most Recent</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Col>
      <Col sm={3} >
        <Form.Control
          type="text"
          placeholder="Search"
          className="me-2 searchField"
          aria-label="Search"
          value={searchValue}
          onChange={doSetSearchValue}
          onKeyDown={handleKeyDown}
        />
      </Col>
    </Row>

    <Row className="p-3">
      {movieList.map(movie => (
        <Col key={movie.id}>
          <Card className="my-2"
              style={{
                width: '15rem'
              }}>
            <Card.Link>
              <LinkContainer to={{pathname: `/movieDetail/${movie.id}`}} >
                <Nav.Link><Card.Img variant="top" src={movie.posterPath} alt={movie.title} width="10%" /></Nav.Link>
              </LinkContainer></Card.Link>
            <Card.Body>
              {/*<Card.Title>{movie.title}</Card.Title>*/}
              {/*<Card.Subtitle className="mb-2 text-muted moveSubtitle"*/}
              {/*               tag="h6">{movie.tagline}</Card.Subtitle>*/}
              <Card.Text>
                <div>
                  <span className='float-start'>Artist: zach </span>
                  <span className='float-end'><Icon.HandThumbsUpFill className="icon-colour"/> Likes: 50</span>
                </div>
                {/*{movie.overview}*/}
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      ))}
    </Row>
  </Form>
  );
}
export default MovieGrid;