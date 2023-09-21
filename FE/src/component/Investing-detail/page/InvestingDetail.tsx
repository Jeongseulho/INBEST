import { motion } from "framer-motion";
import Summary from "../organisms/Summary";
import StockChart from "../organisms/StockChart";
import FinancialAndNews from "../organisms/FinancialAndNews";

interface Props {
  companyCode: string;
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const InvestingDetail = ({ companyCode, setCompanyCode }: Props) => {
  return (
    <>
      <motion.div
        initial="hidden"
        animate="visible"
        exit="exit"
        variants={{
          hidden: { opacity: 0 },
          visible: { opacity: 0.5 },
          exit: { opacity: 0 },
        }}
        transition={{
          duration: 0.5,
        }}
        className="fixed inset-0 bg-black z-10"
        onClick={() => setCompanyCode("")}
      ></motion.div>

      <motion.div
        initial="hidden"
        animate="visible"
        exit="exit"
        variants={{
          hidden: { x: "100%" },
          visible: { x: "0%" },
          exit: { x: "100%" },
        }}
        transition={{
          type: "spring",
          stiffness: 100,
          damping: 20,
        }}
        className="absolute top-0 right-0 w-3/5 h-screen bg-gray-100 z-50"
      >
        <Summary companyCode={companyCode} />
        <FinancialAndNews companyCode={companyCode} />
        <StockChart companyCode={companyCode} />
      </motion.div>
    </>
  );
};

export default InvestingDetail;
