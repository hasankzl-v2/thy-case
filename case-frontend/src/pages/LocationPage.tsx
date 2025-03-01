import { useState, useEffect } from "react";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import Button from "@mui/material/Button";
function LocationPage() {
  const [data, setData] = useState<ILocationData[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    LocationService.getAll()
      .then((response) => setData(response.data))
      .catch(() => setError("Veri çekilirken hata oluştu"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  return (
    <div>
      LocationPage
      <h2>Veriler</h2>
      <ul>
        <Button variant="contained">Hello world</Button>
        {data.map((item: ILocationData, index: number) => (
          <li key={index}>{item.city}</li>
        ))}
      </ul>
    </div>
  );
}

export default LocationPage;
