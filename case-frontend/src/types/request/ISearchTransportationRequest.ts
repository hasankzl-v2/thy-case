export default interface ISearchTransportationRequest {
    id:number | null;
    transportationType: string | null,
    destinationLocationCode: string,
    originLocationCode: string
    operationDays: Array<number>  |null
  }