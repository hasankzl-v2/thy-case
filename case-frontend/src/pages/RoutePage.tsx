import { Box, Button, IconButton, Paper } from "@mui/material";
import { GridColDef } from "@mui/x-data-grid";
import React, { useState } from "react";
import SearchableLocationSelect from "../components/SearchableLocationSelect";
import ILocationData from "../types/ILocationData";
import { emptyFindValidRouteRequest, emptyLocation } from "../Constants";
import SearchIcon from "@mui/icons-material/Search";
import DatePickerComponent from "../components/DatePickerComponent";
import { toast } from "react-toastify";
import RouteService from "../service/RouteService";
import IFindValidRoutesRequest from "../types/request/IFindValidRoutesRequest";
function RoutePage() {
  const [destination, setDestination] = useState<ILocationData>(emptyLocation);
  const [origin, setOrigin] = useState<ILocationData>(emptyLocation);
  const [selectedDate, setSelectedDate] = useState<string>("");
  const columns: GridColDef[] = [
    { field: "id", headerName: "ID", width: 130, filterable: true },
    { field: "name", headerName: "Name", width: 470, filterable: true },
    { field: "country", headerName: "Country", width: 330, filterable: true },
    {
      field: "locationCode",
      headerName: "Location Code",
      width: 230,
      filterable: true,
    },
    {
      field: "Actions",
      headerName: "Actions",
      description: "Column Actions.",
      sortable: false,
      flex: 1,
      renderCell: (params) => {
        return (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <IconButton onClick={() => handleEditClick(params.row)}>
              <SearchIcon />
            </IconButton>
          </div>
        );
      },
    },
  ];
  const handleEditClick = (d: any) => {};
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
      origin.locationCode == null ||
      destination.locationCode == null ||
      selectedDate == null
    ) {
      debugger;
      toast.error("all field should be selected");
    } else {
      const data: IFindValidRoutesRequest = emptyFindValidRouteRequest;
      data.departureDate = selectedDate;
      data.endLocationId = destination.id;
      data.startLocationId = origin.id;
      RouteService.findValidRoutes(data);
    }
  };
  return (
    <Paper sx={{ mt: 5, height: 800, width: "100%" }}>
      <Box sx={{ display: "flex", gap: 2, paddingBottom: 2, paddingTop: 2 }}>
        <SearchableLocationSelect
          label="origin"
          handleSelect={handleSelectOrigin}
          selectedData={origin?.locationCode}
        />
        <SearchableLocationSelect
          label="destination"
          handleSelect={handleSelectDest}
          selectedData={destination?.locationCode}
        />
        <DatePickerComponent handleSelect={setSelectedDate} />
        <Button variant="contained" onClick={search}>
          Search
        </Button>
      </Box>
    </Paper>
  );
}

export default RoutePage;
