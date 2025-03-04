import ILocationData from "./ILocationData";

export default interface IRouteData {
    location:ILocationData,
    transportationType:string,
    transferType:string,
    operationDays:Array<number>
  }