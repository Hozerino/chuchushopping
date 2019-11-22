var alreadyVisited = [];
const BLOCK_SIZE = 100;
const SHIFT_SIZE = BLOCK_SIZE + 5;
var squares;

window.onload = function () {
  intializeDropDown();
  squares = getSpaces();
  // squares = [{ "name": "Shopping_Ibirapuera", "type": "CommercialCenter", "floor": 1, "leftOf": "Parede1-3", "rightOf": "Parede1-2", "bottomOf": "Corredor1-2" }, { "name": "Corredor1-2", "type": "Walkable", "floor": 1, "topOf": "Shopping_Ibirapuera", "leftOf": "Corredor1-3", "rightOf": "Corredor1-1", "bottomOf": "Fonte1-1" }, { "name": "Fonte1-1", "type": "Obstacle", "floor": 1, "topOf": "Corredor1-2", "leftOf": "Corredor1-5", "rightOf": "Corredor1-4", "bottomOf": "Corredor1-7" }, { "name": "Corredor1-7", "type": "Walkable", "floor": 1, "topOf": "Fonte1-1", "leftOf": "Corredor1-8", "rightOf": "Corredor1-6" }, { "name": "Corredor1-8", "type": "Walkable", "floor": 1, "topOf": "Corredor1-5", "leftOf": "Elevador1", "rightOf": "Corredor1-7" }, { "name": "Corredor1-5", "type": "Walkable", "floor": 1, "topOf": "Corredor1-3", "leftOf": "Loja1-2_2", "rightOf": "Fonte1-1", "bottomOf": "Corredor1-8" }, { "name": "Corredor1-3", "type": "Walkable", "floor": 1, "topOf": "Parede1-3", "leftOf": "Loja1-2_1", "rightOf": "Corredor1-2", "bottomOf": "Corredor1-5" }, { "name": "Parede1-3", "type": "Obstacle", "floor": 1, "leftOf": "Parede1-4", "rightOf": "Shopping_Ibirapuera", "bottomOf": "Corredor1-3" }, { "name": "Parede1-4", "type": "Obstacle", "floor": 1, "rightOf": "Parede1-3", "bottomOf": "Loja1-2_1" }, { "name": "Loja1-2_1", "type": "Walkable", "floor": 1, "topOf": "Parede1-4", "rightOf": "Corredor1-3", "bottomOf": "Loja1-2_2", "store": "Xiaomi" }, { "name": "Loja1-2_2", "type": "Walkable", "floor": 1, "topOf": "Loja1-2_1", "rightOf": "Corredor1-5", "bottomOf": "Elevador1", "store": "Xiaomi" }, { "name": "Elevador1", "connects": "Elevador2", "type": "Gateway", "floor": 1, "topOf": "Loja1-2_2", "rightOf": "Corredor1-8" }, { "name": "Elevador2", "connects": "Elevador1", "type": "Gateway", "floor": 2, "topOf": "Loja2-2_2", "rightOf": "Corredor2-8" }, { "name": "Loja2-2_2", "type": "Walkable", "floor": 2, "topOf": "Loja2-2_1", "rightOf": "Corredor2-5", "bottomOf": "Elevador2", "store": "Xiaomi" }, { "name": "Loja2-2_1", "type": "Walkable", "floor": 2, "topOf": "Parede2-4", "rightOf": "Corredor2-3", "bottomOf": "Loja2-2_2", "store": "Xiaomi" }, { "name": "Parede2-4", "type": "Obstacle", "floor": 2, "rightOf": "Parede2-3", "bottomOf": "Loja2-2_1" }, { "name": "Parede2-3", "type": "Obstacle", "floor": 2, "leftOf": "Parede2-4", "rightOf": "Parede2-CC", "bottomOf": "Corredor2-3" }, { "name": "Corredor2-3", "type": "Walkable", "floor": 2, "topOf": "Parede2-3", "leftOf": "Loja2-2_1", "rightOf": "Corredor2-2", "bottomOf": "Corredor2-5" }, { "name": "Corredor2-5", "type": "Walkable", "floor": 2, "topOf": "Corredor2-3", "leftOf": "Loja2-2_2", "rightOf": "Fonte2-1", "bottomOf": "Corredor2-8" }, { "name": "Corredor2-8", "type": "Walkable", "floor": 2, "topOf": "Corredor2-5", "leftOf": "Elevador2", "rightOf": "Corredor2-7" }, { "name": "Corredor2-7", "type": "Walkable", "floor": 2, "topOf": "Fonte2-1", "leftOf": "Corredor2-8", "rightOf": "Corredor2-6" }, { "name": "Fonte2-1", "type": "Obstacle", "floor": 2, "topOf": "Corredor2-2", "leftOf": "Corredor2-5", "rightOf": "Corredor2-4", "bottomOf": "Corredor2-7" }, { "name": "Corredor2-2", "type": "Walkable", "floor": 2, "topOf": "Parede2-CC", "leftOf": "Corredor2-3", "rightOf": "Corredor2-1", "bottomOf": "Fonte2-1" }, { "name": "Parede2-CC", "type": "Obstacle", "floor": 2, "leftOf": "Parede2-3", "rightOf": "Parede2-2", "bottomOf": "Corredor2-2" }, { "name": "Parede2-2", "type": "Obstacle", "floor": 2, "leftOf": "Parede2-CC", "rightOf": "Parede2-1", "bottomOf": "Corredor2-1" }, { "name": "Corredor2-1", "type": "Walkable", "floor": 2, "topOf": "Parede2-2", "leftOf": "Corredor2-2", "rightOf": "Parede2-5", "bottomOf": "Corredor2-4" }, { "name": "Corredor2-4", "type": "Walkable", "floor": 2, "topOf": "Corredor2-1", "leftOf": "Fonte2-1", "rightOf": "Loja2-1_1", "bottomOf": "Corredor2-6" }, { "name": "Corredor2-6", "type": "Walkable", "floor": 2, "topOf": "Corredor2-4", "leftOf": "Corredor2-7", "rightOf": "Loja2-1_2" }, { "name": "Loja2-1_2", "type": "Walkable", "floor": 2, "topOf": "Loja2-1_1", "leftOf": "Corredor2-6", "store": "Americanas" }, { "name": "Loja2-1_1", "type": "Walkable", "floor": 2, "topOf": "Parede2-5", "leftOf": "Corredor2-4", "bottomOf": "Loja2-1_2", "store": "Americanas" }, { "name": "Parede2-5", "type": "Obstacle", "floor": 2, "topOf": "Parede2-1", "leftOf": "Corredor2-1", "bottomOf": "Loja2-1_1" }, { "name": "Parede2-1", "type": "Obstacle", "floor": 2, "leftOf": "Parede2-2", "bottomOf": "Parede2-5" }, { "name": "Americanas", "type": "Unknown", "floor": 0 }, { "name": "Xiaomi", "type": "Unknown", "floor": 0 }, { "name": "Corredor1-6", "type": "Walkable", "floor": 1, "topOf": "Corredor1-4", "leftOf": "Corredor1-7", "rightOf": "Loja1-1_2" }, { "name": "Corredor1-4", "type": "Walkable", "floor": 1, "topOf": "Corredor1-1", "leftOf": "Fonte1-1", "rightOf": "Loja1-1_1", "bottomOf": "Corredor1-6" }, { "name": "Corredor1-1", "type": "Walkable", "floor": 1, "topOf": "Parede1-2", "leftOf": "Corredor1-2", "rightOf": "Parede1-5", "bottomOf": "Corredor1-4" }, { "name": "Parede1-2", "type": "Obstacle", "floor": 1, "leftOf": "Shopping_Ibirapuera", "rightOf": "Parede1-1", "bottomOf": "Corredor1-1" }, { "name": "Parede1-1", "type": "Obstacle", "floor": 1, "leftOf": "Parede1-2", "bottomOf": "Parede1-5" }, { "name": "Parede1-5", "type": "Obstacle", "floor": 1, "topOf": "Parede1-1", "leftOf": "Corredor1-1", "bottomOf": "Loja1-1_1" }, { "name": "Loja1-1_1", "type": "Walkable", "floor": 1, "topOf": "Parede1-5", "leftOf": "Corredor1-4", "bottomOf": "Loja1-1_2", "store": "Americanas" }, { "name": "Loja1-1_2", "type": "Walkable", "floor": 1, "topOf": "Loja1-1_1", "leftOf": "Corredor1-6", "store": "Americanas" }];;

  const mapDiv = $('#map');

  var numberOfFloors = 1;
  squares.forEach(sq => { if (sq.floor > numberOfFloors) numberOfFloors = sq.floor });
  for (var i = 1; i <= numberOfFloors; i++) {
    initialSquare = squares.find(sq => sq.floor === i);
    var floorDiv = document.createElement("div");
    floorDiv.id = "floor-" + i;

    // $(floorDiv).addClass("col-sm-6");
    $(floorDiv).addClass("floor");
    $(mapDiv).append(floorDiv);

    createMap(initialSquare.name, initialSquare.type, initialSquare.bottomOf, initialSquare.leftOf, initialSquare.rightOf, initialSquare.topOf, initialSquare.store, floorDiv);

    alreadyVisited = [];
  }
};

const createMap = (name, type, top, right, left, bottom, store, floorDiv) => {
  var squareElement = document.createElement("div");
  setColor(squareElement, type, store);
  $(floorDiv).append(squareElement);

  $(squareElement).addClass("square");
  squareElement.id = name;
  $(squareElement).text(name);

  alreadyVisited.push(name);
  createNeighbors(squareElement, top, right, left, bottom, floorDiv);
}

const createRightNeighbor = (myElement, neighborName, floorDiv) => {
  if (!neighborName) return;

  let actualTop = parseInt($(myElement).css('top'));
  let actualLeft = parseInt($(myElement).css('left'));

  let neighborLeft = actualLeft + SHIFT_SIZE; // 40 pq eh 2 vezes o tamanho (meu tamanho + o dele) -  top e left inicial

  const neighborElement = document.createElement("div");
  $(neighborElement).addClass("square");
  $(neighborElement).text(neighborName);
  neighborElement.id = neighborName;
  $(neighborElement).css('left', neighborLeft + "px");
  $(neighborElement).css('top', actualTop + "px");

  alreadyVisited.push(neighborName);
  $(floorDiv).append(neighborElement);


  let thisSpace = squares.find(square => square.name === neighborName);
  setColor(neighborElement, thisSpace.type, thisSpace.storeLabel);
  createNeighbors(neighborElement, thisSpace.bottomOf, thisSpace.leftOf, thisSpace.rightOf, thisSpace.topOf, floorDiv);
}

const createBottomNeighbor = (myElement, neighborName, floorDiv) => {
  if (!neighborName) return;

  let actualTop = parseInt($(myElement).css('top'));
  let actualLeft = parseInt($(myElement).css('left'));

  let neighborTop = actualTop + SHIFT_SIZE; // 40 pq eh 2 vezes o tamanho (meu tamanho + o dele) -  top e left inicial

  const neighborElement = document.createElement("div");
  $(neighborElement).addClass("square");
  $(neighborElement).text(neighborName);
  neighborElement.id = neighborName;
  $(neighborElement).css('top', neighborTop + "px");
  $(neighborElement).css('left', actualLeft + "px");

  alreadyVisited.push(neighborName);
  $(floorDiv).append(neighborElement);


  let thisSpace = squares.find(square => square.name === neighborName);
  setColor(neighborElement, thisSpace.type, thisSpace.storeLabel);
  createNeighbors(neighborElement, thisSpace.bottomOf, thisSpace.leftOf, thisSpace.rightOf, thisSpace.topOf, floorDiv);
}


const createLeftNeighbor = (myElement, neighborName, floorDiv) => {
  if (!neighborName) return;
  var isFirst = parseInt($(myElement).css('left')) === 10;

  if (isFirst) {
    alreadyVisited.forEach(spaceName => {
      shiftRight(spaceName);
    })
  }
  let actualTop = parseInt($(myElement).css('top'));
  let actualLeft = parseInt($(myElement).css('left'));
  let neighborLeft = actualLeft - SHIFT_SIZE; // 40 pq eh 2 vezes o tamanho (meu tamanho + o dele) -  top e left inicial

  const neighborElement = document.createElement("div");
  $(neighborElement).addClass("square");
  $(neighborElement).text(neighborName);
  neighborElement.id = neighborName;
  $(neighborElement).css('left', neighborLeft + "px");
  $(neighborElement).css('top', actualTop + "px");

  alreadyVisited.push(neighborName);
  $(floorDiv).append(neighborElement);


  let thisSpace = squares.find(square => square.name === neighborName);
  setColor(neighborElement, thisSpace.type, thisSpace.storeLabel);
  createNeighbors(neighborElement, thisSpace.bottomOf, thisSpace.leftOf, thisSpace.rightOf, thisSpace.topOf, floorDiv);
}

const createTopNeighbor = (myElement, neighborName, floorDiv) => {
  if (!neighborName) return;
  var isFirst = parseInt($(myElement).css('top')) === 10;

  if (isFirst) {
    alreadyVisited.forEach(spaceName => {
      shiftDown(spaceName);
    })
  }
  let actualTop = parseInt($(myElement).css('top'));
  let actualLeft = parseInt($(myElement).css('left'));
  let neighborTop = actualTop - SHIFT_SIZE; // 40 pq eh 2 vezes o tamanho (meu tamanho + o dele) -  top e left inicial

  const neighborElement = document.createElement("div");
  $(neighborElement).addClass("square");
  $(neighborElement).text(neighborName);
  neighborElement.id = neighborName;
  $(neighborElement).css('top', neighborTop + "px");
  $(neighborElement).css('left', actualLeft + "px");

  alreadyVisited.push(neighborName);
  $(floorDiv).append(neighborElement);

  let thisSpace = squares.find(square => square.name === neighborName);
  setColor(neighborElement, thisSpace.type, thisSpace.storeLabel);
  createNeighbors(neighborElement, thisSpace.bottomOf, thisSpace.leftOf, thisSpace.rightOf, thisSpace.topOf, floorDiv);
}


const shiftRight = spaceName => {
  const element = $(`#${spaceName}`)
  let actualLeft = parseInt($(element).css('left'));

  let newLeft = actualLeft + SHIFT_SIZE;
  $(element).css('left', newLeft + "px");
}

const shiftDown = spaceName => {
  const element = $(`#${spaceName}`)
  let actualTop = parseInt($(element).css('top'));

  let newTop = actualTop + SHIFT_SIZE;
  $(element).css('top', newTop + "px");
}

const createNeighbors = (squareElement, top, right, left, bottom, floorDiv) => {
  if (!alreadyVisited.includes(right))
    createRightNeighbor(squareElement, right, floorDiv);
  if (!alreadyVisited.includes(bottom))
    createBottomNeighbor(squareElement, bottom, floorDiv);
  if (!alreadyVisited.includes(left))
    createLeftNeighbor(squareElement, left, floorDiv);
  if (!alreadyVisited.includes(top))
    createTopNeighbor(squareElement, top, floorDiv);
}

const setColor = (squareElement, type, store) => {
  var color = "gray";
  switch (type) {
    case "Obstacle":
      color = "red";
      break;
    case "Walkable":
      color = "green";
      break;
    case "Store":
      color = "blue";
      break;
    case "Gateway":
      color = "yellow";
      break;
    case "CommercialCenter":
      color = "orange";
      break;
  }

  if (store) {
    color = "purple";
  }

  $(squareElement).css('background', color);
}

function findShortestPath() {
  var store = getStoreFromDropDown();
  var path = getPathToStore(store, "1");
  console.log(path);
}

// DROPDOWN
function intializeDropDown() {
  const stores = getLojas();
  // const stores = ["Gendai", "Americanas"];
  stores.forEach(store => {
    $('#dropdown').append(
      $('<option></option>').val(store).text(store)
    );
  });
}

function getStoreFromDropDown() {
  return $('#dropdown').val();
}