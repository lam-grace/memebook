import React, {useEffect, useState} from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {getMovieDetail, saveMovieDetail} from "../../api/MovieApi";
import {useNavigate, useParams} from "react-router-dom";
import {ListGroup, ListGroupItem} from "reactstrap";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import {Button, ButtonGroup, InputGroup} from "react-bootstrap";
import Moment from 'moment'

import ImageComponent from "./ImageComponent";
import Draggable from "react-draggable";
import {LinkContainer} from "react-router-bootstrap";
import Nav from "react-bootstrap/Nav";
import {getUser} from "../../api/UserApi";

import {assignTag, deleteImage, getAllTags, getTagsByImageId, createTag} from "../../api/ImageApi";
import {createMeme, getMemeList} from "../../api/MemeApi";
import {getElementFromSelector} from "bootstrap/js/src/util";



import { SketchPicker } from "react-color";
import Fade from "react-reveal/Fade";
import MemeComponent from "./MemeComponent";
import * as Icon from "react-bootstrap-icons";




const CreateMeme = () => {
    //gets the image id passed in the URL
    let {imageId} = useParams();

    const [user, setUser] = useState('');
    useEffect(() => {
        const getAuthor = async () => {
            const data = await getUser();
            setUser(data);
        }
        getAuthor();
    }, []);

    //color stuff

    const [sketchPickerColor, setSketchPickerColor] = useState("#37d67a");



    let memeOutput = {
        imageId:0,
        height:0,
        width:0,
        color:"#000000",
        fontSize:"0px",
        textList:[{memeText:"", xPos:0, yPos:0}]
        //in text list put text, x, y, color, font
    }
    const [memeDetail, setMemeDetail] = useState(memeOutput);
    const [showSubmitModal, setShowSubmitModal] = useState(false);


    //const to hold state for input boxes
    const [textBoxList, setTextBoxList] = useState([{memeText: ""}]);
    const [x, setX]= useState(0);
    const [y, setY]= useState(0);


    const NavigateToCreateMemes = () =>{
        navigate({pathname: `/memeTemplate`});
    };

    const NavigateToViewMemes= () =>{
        navigate({pathname: `/memeGrid`});
    };


    //to add an input box
    const addTextBox = () => {
        setTextBoxList([...textBoxList, {memeText: ""}]);

    };

    //to remove textbox
    const removeTextBox = (index) => {
        const list = [...textBoxList];
        list.splice(index, 1);
        setTextBoxList(list);

        let newMemeTextArray = [];
        for (let i=0; i<memeDetail.textList.length;i++){
            if (parseInt(memeDetail.textList[i].index) !== index){
                newMemeTextArray.push(memeDetail.textList[i]);
            }
        }
        memeDetail.textList = newMemeTextArray;
        setMemeDetail(memeDetail);
        console.log(memeDetail);
    };

    //e = event
    const handleInputChange = (e, index) => {
        const {name, value} = e.target;
        const list = [...textBoxList];
        list[index][name] = value;
        //update text box
        setTextBoxList(list);
    };

    const handleClick=(e)=>{
   //setting all the details in memeDetail so that it can be posted to Api
        memeDetail.height=document.getElementById("memeImage").offsetHeight;
        memeDetail.width=document.getElementById("memeImage").offsetWidth;
        memeDetail.imageId=parseInt(imageId);
        memeDetail.color=`${sketchPickerColor}`;
        memeDetail.fontSize=parseInt(`${fontSize}`);

        console.log(memeDetail);
        // setShowSubmitModal(true);

        const create = async () => {
            await createMeme(memeDetail).then(data => {
                // navigate("/memeGrid");
                setShowSubmitModal(true);
            });
        }
        create();

    }





    const handleStop =(e, data)=> {
        setX(data.x);
        setY(data.y);
        let singleMeme = {
            index: data.node.id,
            memeText: textBoxList[data.node.id].memeText,
            xPos: data.x,
            yPos: data.y
        }
        let foundOne = false;
        for (let i = 0; i < memeDetail.textList.length; i++) {
            if (memeDetail.textList[i].index === data.node.id) {
                memeDetail.textList[i] = singleMeme;
                foundOne = true;
            }
        }
        if (!foundOne) {
            memeDetail.textList.push(singleMeme);
        }
        setMemeDetail(memeDetail);
        // memeOutput.textList = [...memeOutput.textList, singleMeme]


        console.log(data);
        console.log(data.node.id);
        //console.log(textBoxList[data.node.id].memeText);
        console.log(memeDetail);


    };
    let navigate = useNavigate();
    const handleDelete = () => {
        const cancel = async () => {
            await deleteImage(imageId).then(data => {
                navigate("/memeTemplate");
            });
        }
        cancel();
    };

    //font stuff
    const [fontSize, setFontSize]= useState(30);

    const handleFontInputChange= (event)=>{
       setFontSize(event.target.value);
       console.log(fontSize);

    }

    const handleFontIncreaseClick=()=>{
        setFontSize(fontSize+2);
        console.log("hello", fontSize);
    }

    const handleFontDecreaseClick=()=>{
        setFontSize(fontSize-2);
        console.log("hello", fontSize);
    }


    //tags stuff

    const [showModal, setShowModal] = useState('');
    const handleClose = () => {
        setShowModal(false);
    }

    let tagOutput = {
        imageId:0,
        tagId:0,
        tagName:''
        //in text list put text, x, y, color, font
    }
    const [tagDetail, setTagDetail] = useState(tagOutput);

    const [currentTags, setCurrentTags] = useState([]);
    useEffect(() => {
        const getTags = async () => {
            const data = await getTagsByImageId(imageId);
            setCurrentTags(data);
        }
        getTags();
    }, []);

    const handleAssign = (event, param) => {
        if (event !== undefined) event.preventDefault();
        console.log(imageId, param);
        tagDetail.tagId = param;
        tagDetail.imageId = parseInt(imageId);
        console.log(tagDetail);
        const search = async () => {
            assignTag(tagDetail).then(data=>{
                getTagsByImageId(imageId).then( data => {
                    setCurrentTags(data);
                    }
                );
            });
        }
        search();
    };

    const [allTags, setAllTags] = useState([]);
    useEffect(() => {
        const getTags = async () => {
            getAllTags(imageId).then( data => {
                setAllTags(data);
            })
            // const data = await getAllTags();
        }
        getTags();
    }, [allTags]);


    const handleTags = () => {
        const openModal = async () => {
            setShowModal(true);
        }
        openModal();
    }

    const [tagAdded, setTagAdded] = useState(tagOutput);
    const [tagName, setTagName] = useState('');
    const handleAddTag = (event) => {
        event.preventDefault();
        tagAdded.tagName = tagName;
        tagAdded.imageId = parseInt(imageId);
        const addTagToList = async () => {
            createTag(tagAdded).then(data=>{
                getTagsByImageId(imageId).then(data => {
                        setCurrentTags(data);
                    });
            });
        }
        addTagToList();
    }

    const handleUserInput = (event) => {
        event.preventDefault();
        setTagName(event.target.value);
    }

    const handleCloseModal = () => {
        setShowModal(false);
    };

    return (
        <>
            <Row>
                <div>
                    <h1 className="p-2 header2">
                        Create Meme:
                    </h1>
                </div>
            </Row>
            <Row>
                <Col key="imageCard" id="imageCard" >
                    <Card className="my-2"
                          style={{
                              minWidth: '40rem',
                              maxHeight: '50rem'
                          }}>
                        {/*<div className='text-center' id="memeImage">*/}
                        <div id="imageMeme" style={{
                            display:'contents'
                        }}>
                            <ImageComponent imageId={imageId} cssClass="bigMeme" altText="image"/>
                        </div>


                        {/*</div>*/}
                        {/*<Card.Img variant="top" src={} width="10%"/>*/}
                        <Card.ImgOverlay id="memeImage" style={{
                            padding: '0px',
                            margin:'0px'

                        }}>
                            {textBoxList &&
                            textBoxList.map((item, index) => (
                                <Draggable bounds= "parent" defaultPosition={{x:0, y:0}} onStop={handleStop} >

                                    <div key={index} id={index} style={{
                                        height: 'fit-content',
                                        width:'fit-content',
                                        margin:'0px',
                                        position:'absolute',
                                        left:'0px',
                                        top:'0px'
                                    }}>
                                        {item.memeText && <p style={{
                                            color: `${sketchPickerColor}`,
                                            fontFamily: 'arial',
                                            fontWeight:'bold',
                                            fontSize: `${fontSize}px`,
                                            display: 'inline-block',
                                            height: 'fit-content',
                                            width: 'fit-content',
                                            margin:'0px',
                                            padding:'0px'
                                        }}>{item.memeText}</p>}

                                    </div>
                                </Draggable>
                            ))}

                        </Card.ImgOverlay>

                    </Card>


                    <div className='text-center'>
                        <Button variant="outline-warning" className='float-none' onClick={handleClick}>Save</Button>{' '}
                    </div>

                    {user.role === window.ADMIN &&
                    <div className='text-left'>
                        <Button variant="outline-danger" onClick={handleDelete} type={"submit"} className="mx-2"> Delete Image </Button>
                        <Button variant="outline-primary" onClick={handleTags} type={"submit"}> Add Tags </Button>

                    </div>
                    }
            </Col>

                <Col key="titleCard">
                    <Card className="my-2">

                        <Card.Body>

                            <Form>
                                {textBoxList.map((item, index) => {
                                    return (
                                        <InputGroup className="mb-3">
                                            <Form.Control
                                                name={"memeText"}
                                                type="text"
                                                id={index}
                                                value={item.memeText}
                                                onChange={(e) => handleInputChange(e, index)}
                                            />
                                            <span className='float-end'>
                                                <Button variant="outline-danger" type="button" id="button-addon2" onClick={()=>removeTextBox(index)}>
                                                     Remove
                                                        </Button>
                                             </span>

                                        </InputGroup>


                                    );
                                })}

                                <div>
                                    <span className='float-start'>
                                    <Button variant="outline-secondary" className="addButton" type="button" onClick={addTextBox}>{' '}
                                        Add Text Box
                                    </Button>
                                    </span>


                                </div>
                                <br>
                                </br>
                                <br>

                                </br>

                                <Card.Text>


                                    <div className="sketchpicker">
                                        <h6>Text colour</h6>

                                        {/* Sketch Picker from react-color and handling color on onChange event */}
                                        <SketchPicker
                                            onChange={(color) => {
                                                setSketchPickerColor(color.hex);
                                            }}
                                            color={sketchPickerColor}
                                        />
                                    </div>
                                    <br/>

                                        <span className='float-start'>
                                            <h6>Text size</h6>
                                                <Button variant="outline-secondary" className="addButton mx-2" type="button" id="button-addon2" onClick={()=>handleFontIncreaseClick()}>
                                                     Increase
                                                        </Button>
                                               <Button variant="outline-secondary" className="addButton" type="button" id="button-addon2" onClick={()=>handleFontDecreaseClick()}>
                                                     Decrease
                                                        </Button>
                                             </span>





                                </Card.Text>



                            </Form>




                        </Card.Body>
                    </Card>
                </Col>

            </Row>
            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Assign Tags to image or create new tags</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Current tags
                    <Row className="p-1">
                        {currentTags.map(tag => (
                            <Col key={tag.tagId} md="auto">
                                <Card className="my-2 d-inline-block"
                                      style={{
                                          width: 'fit-content'
                                      }}>
                                    <Card.Body >
                                        <Card.Text>
                                            <div><span className='float-start ps-3'>{tag.tagName} </span></div>
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                    delimiter
                    list of all tags existent (if you select one it goes up to the other list)
                    <Row className="p-1">
                        {allTags.map(tag => (
                            <Col key={tag.tagId} md="auto">
                                <Card className="my-2 d-inline-block"
                                      style={{
                                          width: 'fit-content'
                                      }}>
                                    {/*<Card.Link>*/}
                                    {/*    <LinkContainer >*/}
                                    {/*        {tag.tagName}*/}
                                    {/*    </LinkContainer>*/}
                                    {/*</Card.Link>*/}
                                    <Button variant='outline-primary' className='float-start' onClick={event => handleAssign(event, tag.tagId)}>{tag.tagName} </Button>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                    <Form  className="form" onSubmit={handleAddTag}>
                    <Form.Group className="mb-3" key="username">
                        <Form.Label htmlFor="username">Add Tag </Form.Label>
                        <Form.Control type="text" id="tagName" value={tagName} className="me-2" onChange={handleUserInput}/>
                        <Button variant="primary" className="mt-2" onClick={handleAddTag}>Save</Button>
                    </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={handleClose} >Save</Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showSubmitModal} onHide={NavigateToViewMemes}>
                <Modal.Header closeButton>
                    <Modal.Title>Thanks for creating a Meme!</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Admin will review your meme and you will be able to view it shortly
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={NavigateToCreateMemes}>Create More Memes</Button>
                    <Button variant='secondary' onClick={NavigateToViewMemes}>View All Memes</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}
export default CreateMeme;