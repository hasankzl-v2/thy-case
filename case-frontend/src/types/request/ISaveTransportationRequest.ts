import { TransportationTypeEnum } from "../enum/TransportationTypeEnum"

export default interface ISaveTransportationRequest {
    transportationType: TransportationTypeEnum | null,
    destinationLocationId: number| null,
    originLocationId: number| null,
    operationDays: Array<number>  |null
  }