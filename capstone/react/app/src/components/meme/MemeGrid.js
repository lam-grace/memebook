import React, { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {Button} from "reactstrap";
import Form from "react-bootstrap/Form";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {getMemeList, saveMemeDetail} from "../../api/MemeApi";
import {getAllMemes, getPopularMemeList, getRecentMemeList} from "../../api/MemeApi"
import Dropdown from 'react-bootstrap/Dropdown';
import * as Icon from 'react-bootstrap-icons';
import MemeComponent from "./MemeComponent";
import Fade from 'react-reveal/Fade';


const MemeGrid = () => {

  const [memeList, setMemeList] = useState([]);
  useEffect(() => {
    const getMemes = async () => {
      const data = await getAllMemes();
      setMemeList(data);
    }
    getMemes();
  }, []);

  const [searchValue, setSearchValue] = useState("");
  const [numberOfResults, setNumberOfRecords] = useState(0);
  const doSetSearchValue = (event) => {
    setSearchValue(event.target.value);
  };
  const handleSearch = (event) => {
    if (event !== undefined) event.preventDefault();
    const search = async () => {
      if (searchValue === "")
      {
        getAllMemes().then(data=> {
          setMemeList(data)
        });
      } else {
        getMemeList(searchValue).then(data => {
          setMemeList(data);
          // setNumberOfRecords(data.length);
        });
      }
    }
    search();
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      console.log('do validate')
    }
  }

  // const handleSortRecent = (event) => {
  //   if (event !== undefined) event.preventDefault();
  //   const sortRecent = async () => {
  //     getRecentMemeList().then(data=>{
  //       setMemeList(data);
  //     });
  //   }
  //   sortRecent();
  // }

  return (
      <Form onSubmit={handleSearch}>
        <Row className="mb-1">
          <Col sm={8}>
            <Dropdown>
              <Dropdown.Toggle className="sortButton btn-light" id="dropdown-basic">
                Sort By
              </Dropdown.Toggle>

              <Dropdown.Menu>
                <Dropdown.Item href="#/action-1" >Most Popular</Dropdown.Item>
                <Dropdown.Item href="#/action-2" >Most Recent</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Col>
          <Col sm={4} >
            <Form.Control
                type="text"
                placeholder="Search"
                className="me-2 searchField"
                aria-label="Search"
                value={searchValue}
                onChange={doSetSearchValue}
                onKeyDown={handleKeyDown}
                style={{display: "inline-block", float:"right"}}
            />
          </Col>
        </Row>
        <Row className="p-3">
          {memeList.map(meme => (
              <Col key={meme.memeId} md="auto">
                <Fade clear>
                <Card className="my-2 float-start"
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
                        <div >
                          <span className='float-start ps-3 pt-2'>Artist: {meme.author} </span>
                          <span className='float-end pe-3 pt-2'><Icon.HandThumbsUpFill className="icon-colour"/> Likes: {meme.popularity}</span>
                        </div>
                      </Card.Text>
                  </Card.Body>
                </Card>
                </Fade>
              </Col>
          ))}
        </Row>
      </Form>
  );
}
export default MemeGrid;