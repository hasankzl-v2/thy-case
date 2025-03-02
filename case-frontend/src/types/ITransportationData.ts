import ILocationData from "./ILocationData";

export default interface ITransportationData {
    id:number | null;
    transportationType: string,
    destinationLocation: ILocationData,
    originLocation: ILocationData
    operationDays: Array<number> 
  }