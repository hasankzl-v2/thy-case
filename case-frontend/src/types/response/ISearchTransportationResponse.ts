import ITransportationData from "../ITransportationData";

export default interface ITransportationSearchResponse {
    content: Array<ITransportationData>,
    totalElements: number,
    totalPages: number,
    size: number,
    number: number,
  }