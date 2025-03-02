import { TransportationTypeEnum } from "./enum/TransportationTypeEnum";
import ILocationData from "./ILocationData";

export default interface ITransportationData {
    id:number | null;
    transportationType: TransportationTypeEnum | null,
    destinationLocation: ILocationData,
    originLocation: ILocationData
    operationDays: Array<number>  |null
  }