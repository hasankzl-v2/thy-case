import http from "../http-commons";
import ITransportationData from "../types/ITransportationData";
import ISaveLocationRequest from "../types/request/ISaveLocationRequest";
import ISearchTransportationRequest from "../types/request/ISearchTransportationRequest";
import ITransportationSearchResponse from "../types/response/ISearchTransportationResponse";

const getAll = () => {
    return http.get<Array<ITransportationData>>("/transportation/findAll");
  };
  
  const search = (data : ISearchTransportationRequest| {},page:number,size:number) =>{
    return http.post<ITransportationSearchResponse>(`/transportation/search?page=${page}&size=${size}`, data);
  }
  const get = (id: any) => {
    return http.get<ITransportationData>(`/transportation/findById/${id}`);
  };
  
  const create = (data: ISaveLocationRequest) => {
    return http.post<ITransportationData>("/transportation/save", data);
  };
  
  const update = (data: ITransportationData) => {
    return http.put<any>(`/transportation/update`, data);
  };
  
  const remove = (id: any) => {
    return http.delete<any>(`/transportation/deleteById/${id}`);
  };
  
  const TransportationService = {
    getAll,
    get,
    create,
    update,
    remove,
    search
  };
  
  export default TransportationService;
