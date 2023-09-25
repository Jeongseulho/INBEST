import bronze from "../../asset/image/bronze.png";
import silver from "../../asset/image/silver.png";
import gold from "../../asset/image/gold.png";
import platinum from "../../asset/image/platinum.png";
import diamond from "../../asset/image/diamond.png";

interface Props {
  tier: number;
}

const NumberToTierImage = ({ tier }: Props) => {
  return (
    <img
      src={
        tier >= 0 && tier < 100
          ? bronze
          : tier >= 100 && tier < 200
          ? silver
          : tier >= 200 && tier < 300
          ? gold
          : tier >= 300 && tier < 400
          ? platinum
          : diamond
      }
    />
  );
};
export default NumberToTierImage;
