import { useEffect } from "react";
import { useAuth } from "react-oidc-context";
import { useNavigate } from "react-router";

const CallbackPage: React.FC = () => {
  const { isLoading, isAuthenticated, error } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isLoading) {
      return;
    }

    if (isAuthenticated) {
      const redirectPath = localStorage.getItem("redirectPath");
      if (redirectPath) {
        localStorage.removeItem("redirectPath");
        navigate(redirectPath, { replace: true });
      } else {
        navigate("/", { replace: true });
      }
    }
  }, [isLoading, isAuthenticated, navigate]);

  if (isLoading) {
    return <p className="p-4">Processing login...</p>;
  }

  if (error) {
    return (
      <div className="p-8 max-w-md mx-auto text-center">
        <h2 className="text-xl font-bold text-red-600 mb-2">Erreur de connexion</h2>
        <p className="text-gray-600 mb-4">{error.message}</p>
        <button
          onClick={() => {
            localStorage.removeItem("redirectPath");
            navigate("/", { replace: true });
          }}
          className="px-4 py-2 bg-indigo-600 text-white rounded cursor-pointer hover:bg-indigo-700"
        >
          Retour à l'accueil
        </button>
      </div>
    );
  }

  return <p className="p-4">Completing login...</p>;
};

export default CallbackPage;
