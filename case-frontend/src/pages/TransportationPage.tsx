import React, { useEffect, useState } from "react";
import ITransportationData from "../types/ITransportationData";
import TransportationService from "../service/TransportationService";
import ToastComponents from "../components/ToastComponents";
import IErrorResponse from "../types/response/IErrorResponse";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { Edit as EditIcon, Delete as DeleteIcon } from "@mui/icons-material";
import { Box, Button, IconButton, Paper, TextField } from "@mui/material";
const emtpyLocation = {
  id: 0,
  name: "",
  country: "",
  city: "",
  locationCode: "",
};
const emptyTransportation: ITransportationData = {
  transportationType: "",
  originLocation: emtpyLocation,
  destinationLocation: emtpyLocation,
};
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
  const [filters, setFilters] = useState(emtpyLocation);
  const [updateData, setUpdateData] =
    useState<ITransportationData>(emptyTransportation);
  const [openUpdateModal, setOpenUpdateModal] = useState(false);

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
      </Paper>
    </div>
  );
}

export default TransportationPage;
