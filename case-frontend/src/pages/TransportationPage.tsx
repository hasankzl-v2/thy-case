import React, { useEffect, useState } from "react";
import ITransportationData from "../types/ITransportationData";
import TransportationService from "../service/TransportationService";
import ToastComponents from "../components/ToastComponents";
import IErrorResponse from "../types/response/IErrorResponse";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { Edit as EditIcon, Delete as DeleteIcon } from "@mui/icons-material";
import { Box, Button, Chip, IconButton, Paper, TextField } from "@mui/material";
import ConfirmDialog from "../components/ConfirmationDialog";
import { toast } from "react-toastify";
import SearchableSelect from "../components/SearchableSearch";
import MultiSelectDays from "../components/MulitSelectDays";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import ISearchTransportationRequest from "../types/request/ISearchTransportationRequest";
import {
  daysOfWeek,
  emptyLocation,
  emptySearchTransportation,
  emptyTransportation,
} from "../Constants";
import TransportationTypeSelect from "../components/TransportationTypeSelect";
import { TransportationTypeEnum } from "../types/enum/TransportationTypeEnum";
const paginationModel = { page: 0, pageSize: 5 };
function TransportationPage() {
  const [data, setData] = useState<ITransportationData[]>([]);
  const [openDialog, setOpenDialog] = useState(false); // Dialog açık mı kontrolü
  const [selectedData, setSelectedData] =
    useState<ITransportationData | null>(); // Silinecek verinin id'si
  const [loading, setLoading] = useState<boolean>(true);
  const [page, setPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(10);
  const [totalRows, setTotalRows] = useState<number>(0);
  const [filters, setFilters] = useState<ISearchTransportationRequest>(
    emptySearchTransportation
  );
  const [updateData, setUpdateData] =
    useState<ITransportationData>(emptyTransportation);
  const [openUpdateModal, setOpenUpdateModal] = useState(false);
  const [destinationSelectData, setDestinationSelectData] = useState<
    ILocationData[]
  >([]);
  const [destinationSearch, setDestinationSearch] = useState<string>("");
  const [originSelectData, setOriginSelectData] = useState<ILocationData[]>([]);
  const [originSearch, setOriginSearch] = useState<string>("");
  const [selecetedDestinationForSearch, setSelectedDestinationForSearch] =
    useState<ILocationData>(emptyLocation);
  const [selecetedOriginForSearch, setSelectedOriginForSearch] =
    useState<ILocationData>(emptyLocation);

  useEffect(() => {
    fetchDestination();
  }, [destinationSearch]);

  useEffect(() => {
    fetchOrigin();
  }, [originSearch]);

  const handleSelectDest = (locationCode: string) => {
    setFilters({
      ...filters,
      destinationLocationCode: locationCode,
    });
  };

  const handleSelectOrigin = (locationCode: string) => {
    setFilters({
      ...filters,
      originLocationCode: locationCode,
    });
  };
  const fetchDestination = () => {
    const location: ILocationData = {
      ...emptyLocation,
      locationCode: destinationSearch,
    };
    LocationService.search(location, 0, 20).then((res) =>
      setDestinationSelectData(res.data.content)
    );
  };

  const fetchOrigin = () => {
    const location: ILocationData = {
      ...emptyLocation,
      locationCode: originSearch,
    };
    LocationService.search(location, 0, 20).then((res) =>
      setOriginSelectData(res.data.content)
    );
  };

  const handleEditClick = (row: ITransportationData) => {
    setUpdateData(row);
    setOpenUpdateModal(true);
  };

  const handleDeleteClick = (row: ITransportationData) => {
    setSelectedData(row);
    setOpenDialog(true);
  };

  const handleDeleteData = () => {
    if (selectedData != null) {
      TransportationService.remove(selectedData.id).then(() => {
        toast("deleted successfuly");
        setOpenDialog(false);
        refreshTable();
      });
    }
  };
  const handleCancelDeleteData = () => {
    setSelectedData(null);
    setOpenDialog(false);
  };
  const refreshTable = () => {
    setFilters(emptySearchTransportation);
    setPage(0);
    fetchData();
  };
  const columns: GridColDef[] = [
    { field: "id", headerName: "ID", width: 130, filterable: true },
    {
      field: "originLocation",
      headerName: "Origin",
      width: 230,
      filterable: true,
      renderCell: (params) => {
        return <div>{params.row.originLocation.locationCode}</div>;
      },
    },
    {
      field: "destionationLocation",
      headerName: "Destination",
      width: 230,
      filterable: true,
      renderCell: (params) => {
        return <div>{params.row.destinationLocation.locationCode}</div>;
      },
    },
    {
      field: "transportationType",
      headerName: "Transportation Type",
      width: 270,
      filterable: true,
    },
    {
      field: "operationDays",
      headerName: "Operation Days",
      width: 470,
      filterable: true,
      renderCell: (params) => {
        return (
          <Box>
            {params.row.operationDays.map((o, index) => {
              return (
                <Chip
                  key={index}
                  label={daysOfWeek.find((d) => d.value == o)?.label}
                  sx={{
                    backgroundColor: "#1976d2",
                    color: "white",
                    fontWeight: "bold",
                    borderRadius: "16px",
                    ml: 1,
                  }}
                />
              );
            })}
          </Box>
        );
      },
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
              <EditIcon />
            </IconButton>
            <IconButton onClick={() => handleDeleteClick(params.row)}>
              <DeleteIcon />
            </IconButton>
          </div>
        );
      },
    },
  ];

  useEffect(() => {
    fetchData();
  }, [page, pageSize]);

  const search = () => {
    setPage(0);
    fetchData();
  };
  const clear = () => {
    setPage(0);
    setFilters(emptySearchTransportation);
  };
  const fetchData = async () => {
    setLoading(true);
    try {
      TransportationService.search(filters, page, pageSize)
        .then((response) => {
          setData(response.data.content);
          setTotalRows(response.data.totalPages * response.data.size);
        })
        .catch((e: IErrorResponse) => ToastComponents.showErrorToast(e))
        .finally(() => setLoading(false));
    } catch (error) {
      console.error("Error while fetching the data", error);
    } finally {
      setLoading(false);
    }
  };

  const handleDaySelect = (selectedDays: Array<number>) => {
    setFilters({
      ...filters,
      operationDays: selectedDays,
    });
  };

  const handleFilterId = (id: number | null) => {
    setFilters({
      ...filters,
      id,
    });
  };

  const handleFilterTransportationType = (
    transportationType: TransportationTypeEnum | null
  ) => {
    setFilters({
      ...filters,
      transportationType,
    });
  };
  return (
    <div>
      <Paper sx={{ mt: 5, height: 800, width: "100%" }}>
        <div
          style={{
            display: "flex",
            justifyContent: "right",
            alignItems: "right",
          }}
        ></div>
        <Box sx={{ display: "flex", gap: 2, paddingBottom: 2 }}>
          <TextField
            label="Id"
            variant="outlined"
            type="text"
            value={filters.id ?? ""}
            onChange={(e) => {
              if (e.target.value == "") {
                handleFilterId(null);
              } else {
                handleFilterId(Number(e.target.value));
              }
            }}
          />
          <SearchableSelect
            label="origin"
            data={originSelectData}
            handleSelect={handleSelectOrigin}
            selectedData={filters.originLocationCode}
            handleSearch={setOriginSearch}
          />
          <SearchableSelect
            label="destination"
            data={destinationSelectData}
            handleSelect={handleSelectDest}
            selectedData={filters.destinationLocationCode}
            handleSearch={setOriginSearch}
          />

          <TransportationTypeSelect
            selectedType={filters.transportationType}
            handleSelect={handleFilterTransportationType}
          />

          <MultiSelectDays
            handleSelect={handleDaySelect}
            selectedOperationDays={filters.operationDays}
          />
          <Button variant="contained" onClick={search}>
            Search
          </Button>
          <Button variant="contained" onClick={clear}>
            clear
          </Button>
        </Box>
        <DataGrid
          rows={data}
          columns={columns}
          initialState={{ pagination: { paginationModel } }}
          sx={{ border: 0 }}
          pageSizeOptions={[2, 10, 20]}
          rowCount={totalRows}
          paginationMode="server"
          loading={loading}
          onPaginationModelChange={(model) => {
            setPage(model.page);
            setPageSize(model.pageSize);
          }}
        />

        <ConfirmDialog
          message="Are you sure to delete this data?"
          isOpen={openDialog}
          handleCancel={handleCancelDeleteData}
          handleConfirm={handleDeleteData}
        />
      </Paper>
    </div>
  );
}

export default TransportationPage;
