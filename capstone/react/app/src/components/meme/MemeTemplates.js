import React, { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {Button, CardText} from "reactstrap";
import Form from "react-bootstrap/Form";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import Dropdown from 'react-bootstrap/Dropdown';
import {getAllImages, getImageList} from "../../api/ImageApi";
import ImageComponent from "./ImageComponent";
import {getAllMemes} from "../../api/MemeApi";


const MemeTemplate = () => {

    const [imageList, setImageList] = useState([]);
    useEffect(() => {
        const getImages = async () => {
            const data = await getAllImages();
            setImageList(data);
        }
        getImages();
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
                getAllImages().then(data=> {
                    setImageList(data)
                });
            } else {
                getImageList(searchValue).then(data => {
                    console.log("Found:" + data.length + " records");
                    setImageList(data);
                    setNumberOfRecords(data.length);
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

    return (
        <Form onSubmit={handleSearch}>
            <Row className="mb-1">
                <Col sm={9}>
                    <Dropdown>
                        <Dropdown.Toggle className="sortButton btn-light" id="dropdown-basic">
                            Sort By
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item href="#/action-1" >Most Popular</Dropdown.Item>
                            <Dropdown.Item href="#/action-2">Most Recent</Dropdown.Item>
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
                {imageList?.map(image => (
                    <Col key={image.imageId} md="auto">
                        <Card className="my-2"
                              style={{
                                  width: '470px'
                              }}>
                            <Card.Body >
                                <Card.Link>
                                    <LinkContainer to={{pathname: `/createMeme/${image.imageId}`}} >
                                        <Nav.Link><ImageComponent imageId={image.imageId} cssClass="meme" altText="image"/></Nav.Link>
                                    </LinkContainer></Card.Link>
                            </Card.Body>



                        </Card>
                    </Col>
                ))}
            </Row>
        </Form>
    );
}
export default MemeTemplate;