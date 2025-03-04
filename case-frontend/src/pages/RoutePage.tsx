import { Box, Button, IconButton, Paper } from "@mui/material";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import React, { useState } from "react";
import SearchableLocationSelect from "../components/SearchableLocationSelect";
import ILocationData from "../types/ILocationData";
import { emptyFindValidRouteRequest, emptyLocation } from "../Constants";
import SearchIcon from "@mui/icons-material/Search";
import DatePickerComponent from "../components/DatePickerComponent";
import { toast } from "react-toastify";
import RouteService from "../service/RouteService";
import IFindValidRoutesRequest from "../types/request/IFindValidRoutesRequest";
import IValidRoutesResponse from "../types/response/IValidRoutesResponse";
import IRouteData from "../types/IRouteData";
import RouteInspectModal from "../components/RouteInspectModal";
import ButtonComponent from "../components/ButtonComponent";
function RoutePage() {
  const [destination, setDestination] = useState<ILocationData>(emptyLocation);
  const [origin, setOrigin] = useState<ILocationData>(emptyLocation);
  const [data, setData] = useState<IValidRoutesResponse[]>([]);
  const [selectedDate, setSelectedDate] = useState<string>("");
  const [selectedRoute, setSelecetedRoute] = useState<IRouteData[]>([]);
  const [openInspectModal, setOpenInspectModal] = useState<boolean>(false);
  const columns: GridColDef[] = [
    { field: "id", headerName: "ID", width: 130, filterable: true },
    {
      field: "validRoutes",
      headerName: "Available Routes",
      filterable: true,
      flex: 1,
      renderCell: (params) => {
        return <div>via {params.row.validRoutes[1].location.name}</div>;
      },
    },
    {
      field: "transportationType",
      headerName: "Start Transportation Type",
      filterable: true,
      flex: 1,
      renderCell: (params) => {
        return <div>{params.row.validRoutes[0].transportationType}</div>;
      },
    },
    {
      field: "Actions",
      headerName: "Actions",
      description: "Column Actions.",
      sortable: false,
      width: 230,

      renderCell: (params) => {
        return (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <IconButton
              onClick={() => handleInspectClick(params.row.validRoutes)}
            >
              <SearchIcon />
            </IconButton>
          </div>
        );
      },
    },
  ];
  const handleInspectClick = (routeList: Array<IRouteData>) => {
    setSelecetedRoute(routeList);
    setOpenInspectModal(true);
  };
  const handleSelectDest = (location: ILocationData | null) => {
    if (location) {
      setDestination(location);
    }
  };

  const handleSelectOrigin = (location: ILocationData | null) => {
    if (location) {
      setOrigin(location);
    }
  };

  const search = () => {
    if (
      origin.id == null ||
      destination.id == null ||
      selectedDate == null ||
      selectedDate == ""
    ) {
      toast.error("all field should be selected");
    } else {
      const data: IFindValidRoutesRequest = emptyFindValidRouteRequest;
      data.departureDate = selectedDate;
      data.endLocationId = destination.id;
      data.startLocationId = origin.id;
      RouteService.findValidRoutes(data).then((res) => {
        setData(res.data);
      });
    }
  };

  const clear = () => {
    setDestination(emptyLocation);
    setOrigin(emptyLocation);
    setSelectedDate("");
  };
  return (
    <Paper sx={{ mt: 5, height: 800, width: "100%" }}>
      <Box sx={{ display: "flex", gap: 2, paddingBottom: 2, paddingTop: 2 }}>
        <SearchableLocationSelect
          label="From"
          handleSelect={handleSelectOrigin}
          selectedData={origin?.locationCode}
        />
        <SearchableLocationSelect
          label="to"
          handleSelect={handleSelectDest}
          selectedData={destination?.locationCode}
        />
        <DatePickerComponent
          handleSelect={setSelectedDate}
          selectedDate={selectedDate}
        />

        <ButtonComponent text={"Search"} onClick={search} />
        <ButtonComponent text={"Clear"} onClick={clear} />
      </Box>
      <DataGrid
        rows={data}
        columns={columns}
        sx={{ border: 0 }}
        pageSizeOptions={[2, 10, 20]}
        rowCount={30}
        paginationMode="server"
        getRowId={(row) => row.id}
      />
      <RouteInspectModal
        selectedRoute={selectedRoute}
        openInspectModal={openInspectModal}
        closeModal={() => setOpenInspectModal(false)}
      />
    </Paper>
  );
}

export default RoutePage;
