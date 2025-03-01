import ILocationData from "../ILocationData";

export default interface ISearchLocationResponse {
    content: Array<ILocationData>,
    totalElements: number,
    totalPages: number,
    size: number,
    number: number,
  }