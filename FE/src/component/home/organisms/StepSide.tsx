import StepPagination from "../molecules/StepPagination";
import StepDesc from "../molecules/StepDesc";
import { HOME_TEXT } from "../../../constant/HOME_TEXT";
import { motion } from "framer-motion";
import { useObserver } from "../../../hook/useObserver";

interface StepSideProps {
  curStep: number;
}

const StepSide = ({ curStep }: StepSideProps) => {
  const { ref, animation } = useObserver();

  return (
    <div className="sticky top-20 z-50">
      <StepPagination step={curStep} />
      <motion.div
        ref={ref}
        initial="hidden"
        animate={animation}
        variants={{
          visible: { opacity: 1 },
          hidden: { opacity: 0 },
        }}
        transition={{ duration: 0.3 }}
      >
        <StepDesc title={HOME_TEXT[curStep].title} desc={HOME_TEXT[curStep].desc} />
      </motion.div>
    </div>
  );
};
export default StepSide;
