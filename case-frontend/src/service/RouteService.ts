import http from "../http-commons";
import IFindValidRoutesRequest from "../types/request/IFindValidRoutesRequest";
import IValidRoutesResponse from "../types/response/IValidRoutesResponse";
  const findValidRoutes = (data : IFindValidRoutesRequest) =>{
    return http.post<Array<IValidRoutesResponse>>(`/route/findValidRoutes`, data);
  }

  const RouteService = {
    findValidRoutes
  };
  
  export default RouteService;
