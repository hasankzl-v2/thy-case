import { Link } from "react-router-dom";
function NotFoundPage() {
  return (
    <div>
      <div>404 not found</div>
      <br />
      <Link to="/">Return home</Link>
    </div>
  );
}

export default NotFoundPage;
