import http from "../http-commons";
import IFindValidRoutesRequest from "../types/request/IFindValidRoutesRequest";
import ITransportationSearchResponse from "../types/response/ISearchTransportationResponse";
  const findValidRoutes = (data : IFindValidRoutesRequest) =>{
    return http.post<ITransportationSearchResponse>(`/route/findValidRoutes`, data);
  }

  const RouteService = {
    findValidRoutes
  };
  
  export default RouteService;
