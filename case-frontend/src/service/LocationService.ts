import http from "../http-commons";
import ILocationData from "../types/ILocationData";
import ISaveLocationRequest from "../types/request/ISaveLocationRequest";
import IUpdateLocationRequest from "../types/request/IUpdateLocationRequest";
import ISearchLocationResponse from "../types/response/ISearchLocationResponse";

const getAll = () => {
    return http.get<Array<ILocationData>>("/location/findAll");
  };
  
  const search = (data : ILocationData| {},page:number,size:number) =>{
    return http.post<ISearchLocationResponse>(`/location/search?page=${page}&size=${size}`, data);
  }
  const get = (id: any) => {
    return http.get<ILocationData>(`/location/findById/${id}`);
  };
  
  const create = (data: ISaveLocationRequest) => {
    return http.post<ILocationData>("/location/save", data);
  };
  
  const update = (data: ILocationData) => {
    return http.put<any>(`/location/update`, data);
  };
  
  const remove = (id: any) => {
    return http.delete<any>(`/location/deleteById/${id}`);
  };
  
  const LocationService = {
    getAll,
    get,
    create,
    update,
    remove,
    search
  };
  
  export default LocationService;
