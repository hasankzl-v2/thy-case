import IRouteData from "../IRouteData";

export default interface IValidRoutesResponse {
    departureDate: string,
    startLocationId: string,
    endLocationId: string,
    validRoutes: Array<IRouteData>,
    totalElements: number,
    totalPages: number,
    size: number,
    number: number,
  }