import { TransportationTypeEnum } from "../enum/TransportationTypeEnum";

export default interface IUpdateTransportationRequest {
    id:number | null,
    transportationType: TransportationTypeEnum | null,
    destinationLocationId: number| null,
    originLocationId: number| null,
    operationDays: Array<number>  |null
  }