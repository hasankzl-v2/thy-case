import { TransportationTypeEnum } from "../enum/TransportationTypeEnum";

export default interface ISearchTransportationRequest {
    id:number | null;
    transportationType: TransportationTypeEnum | null,
    destinationLocationCode: string,
    originLocationCode: string
    operationDays: Array<number>  |null
  }