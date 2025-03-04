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
import SearchableSelect from "../components/SearchableLocationSelect";
import MultiSelectDays from "../components/MulitSelectDays";
import ISearchTransportationRequest from "../types/request/ISearchTransportationRequest";
import {
  daysOfWeek,
  emptySearchTransportation,
  emptyTransportation,
} from "../Constants";
import SearchIcon from "@mui/icons-material/Search";
import ClearIcon from "@mui/icons-material/Clear";
import TransportationTypeSelect from "../components/TransportationTypeSelect";
import { TransportationTypeEnum } from "../types/enum/TransportationTypeEnum";
import ILocationData from "../types/ILocationData";
import TransportationSaveModal from "../components/TransportationSaveModal";
import TransportationUpdateModal from "../components/TransportationUpdateModal";
import ButtonComponent from "../components/ButtonComponent";
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

  useEffect(() => {
    fetchData();
  }, [page, pageSize]);

  const handleSelectDest = (location: ILocationData | null) => {
    const code = location != null ? location.locationCode : "";
    setFilters({
      ...filters,
      destinationLocationCode: code,
    });
  };

  const handleSelectOrigin = (location: ILocationData | null) => {
    const code = location != null ? location.locationCode : "";
    setFilters({
      ...filters,
      originLocationCode: code,
    });
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
                    backgroundColor: "#e60012",
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
  const handleUpdateCancel = () => {
    setOpenUpdateModal(false);
    setUpdateData(emptyTransportation);
  };
  const handleUpdateConfirm = () => {
    setOpenUpdateModal(false);
    setUpdateData(emptyTransportation);
    refreshTable();
  };

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
    selectedDays = selectedDays.length == 0 ? null : selectedDays;
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

  const handleSave = () => {
    refreshTable();
  };
  return (
    <div>
      <Paper sx={{ mt: 5, mb: 5, height: 800, width: "100%" }}>
        <div
          style={{
            display: "flex",
            justifyContent: "right",
            alignItems: "right",
            marginBottom: 30,
          }}
        >
          <TransportationSaveModal handleSave={handleSave} />
        </div>
        <Box sx={{ display: "flex", gap: 5, paddingBottom: 2 }}>
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
            label="From"
            handleSelect={handleSelectOrigin}
            selectedData={filters.originLocationCode}
          />
          <SearchableSelect
            label="to"
            handleSelect={handleSelectDest}
            selectedData={filters.destinationLocationCode}
          />

          <TransportationTypeSelect
            selectedType={filters.transportationType}
            handleSelect={handleFilterTransportationType}
          />

          <MultiSelectDays
            handleSelect={handleDaySelect}
            selectedOperationDays={filters.operationDays}
          />
        </Box>
        <Box
          sx={{
            display: "flex",
            justifyContent: "flex-end", // Butonları sağa hizalar
            gap: 2, // Butonlar arasında boşluk bırakır
            mt: 2,
            mr: 5,
            mb: 5, // Üstten biraz boşluk ekler (opsiyonel)
          }}
        >
          <ButtonComponent
            icon={<SearchIcon />}
            text={"Search"}
            onClick={search}
          />
          <ButtonComponent
            icon={<ClearIcon />}
            text={"Clear"}
            onClick={clear}
          />
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
      <TransportationUpdateModal
        handleUpdate={handleUpdateConfirm}
        modelOpen={openUpdateModal}
        selectedTransportation={updateData}
        handleCancel={() => setOpenUpdateModal(false)}
      />
    </div>
  );
}

export default TransportationPage;
