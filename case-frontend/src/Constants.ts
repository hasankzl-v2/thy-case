import ITransportationData from "./types/ITransportationData";
import IFindValidRoutesRequest from "./types/request/IFindValidRoutesRequest";
import ISaveTransportationRequest from "./types/request/ISaveTransportationRequest";
import ISearchTransportationRequest from "./types/request/ISearchTransportationRequest";
import IUpdateTransportationRequest from "./types/request/IUpdateTransportationRequest";

export const daysOfWeek = [
  { label: "Monday", value: 1 },
  { label: "Tuesday", value: 2 },
  { label: "Wednesday", value: 3 },
  { label: "Thursday", value: 4 },
  { label: "Friday", value: 5 },
  { label: "Saturday", value: 6 },
  { label: "Sunday", value: 7 },
];

export const emptyLocation = {
    id: null,
    name: "",
    country: "",
    city: "",
    locationCode: "",
  };

  export const transportOptions = ["FLIGHT", "BUS", "SUBWAY", "UBER"];


  export const emptyTransportation: ITransportationData = {
    id: null,
    transportationType: null,
    originLocation: emptyLocation,
    destinationLocation: emptyLocation,
    operationDays: null,
  };
  
  export const emptySearchTransportation: ISearchTransportationRequest = {
    id: null,
    transportationType: null,
    originLocationCode: "",
    destinationLocationCode: "",
    operationDays: null,
  };

  export const emptySaveTransportation :ISaveTransportationRequest ={
    transportationType:null,
    originLocationId:null,
    destinationLocationId:null,
    operationDays:null
  }

  export const emptyUpdateTransportation :IUpdateTransportationRequest={
    id:null,
    transportationType:null,
    originLocationId:null,
    destinationLocationId:null,
    operationDays:null
  }

  export const emptyFindValidRouteRequest : IFindValidRoutesRequest={
    startLocationId: null,
    endLocationId: null,
    departureDate: "" 
  }